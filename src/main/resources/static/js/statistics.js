document.addEventListener('DOMContentLoaded', async function() {
    await loadGroupsSelect();

    const groupSelect = document.getElementById('groupSelect');
    if (groupSelect) {
        groupSelect.addEventListener('change', async function(e) {
            if (e.target.value) {
                await loadGroupStatistics(parseInt(e.target.value));
            }
        });
    }
});

async function loadGroupsSelect() {
    const groups = await getGroups();
    const select = document.getElementById('groupSelect');
    if (!select) return;

    if (groups.length === 0) {
        select.innerHTML = '<option value="">Нет групп</option>';
        return;
    }

    select.innerHTML = '<option value="">Выберите группу</option>' +
        groups.map(function(g) {
            return '<option value="' + g.id + '">' + g.name + '</option>';
        }).join('');
}

async function loadGroupStatistics(groupId) {
    const group = await getGroupById(groupId);
    if (!group) return;

    const athletes = await getAthletes();
    const groupAthletes = athletes.filter(function(a) {
        return a.group && a.group.id === groupId;
    });

    const characteristics = await getCharacteristics();
    const allAthleteCharacteristics = await getAthleteCharacteristics();

    const athleteIds = groupAthletes.map(function(a) { return a.id; });
    const groupCharacteristics = allAthleteCharacteristics.filter(function(ac) {
        return ac.athlete && athleteIds.includes(ac.athlete.id);
    });

    document.getElementById('statsContent').style.display = 'block';

    const averages = calculateGroupAverages(characteristics, groupCharacteristics);

    const labels = Object.keys(averages);
    const values = Object.values(averages);

    if (labels.length > 0) {
        createGroupRadarChart(labels, values, 'Средние показатели: ' + group.name);
    }

    if (groupAthletes.length > 0 && characteristics.length > 0) {
        const datasets = groupAthletes.map(function(athlete) {
            const acs = groupCharacteristics.filter(function(ac) {
                return ac.athlete && ac.athlete.id === athlete.id;
            });

            const data = characteristics.map(function(char) {
                const ac = acs.find(function(a) {
                    return a.characteristic && a.characteristic.id === char.id;
                });
                return ac ? ac.value : 0;
            });

            return {
                label: athlete.name,
                data: data
            };
        });

        createComparisonChart(characteristics.map(function(c) { return c.name; }), datasets);
    }

    fillStatsTable(groupAthletes, characteristics, groupCharacteristics);
}

function fillStatsTable(athletes, characteristics, athleteCharacteristics) {
    const tbody = document.querySelector('#statsTable tbody');
    if (!tbody) return;

    tbody.innerHTML = '';

    if (athletes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align: center;">Нет спортсменов в группе</td></tr>';
        return;
    }

    athletes.forEach(function(athlete) {
        const acs = athleteCharacteristics.filter(function(ac) {
            return ac.athlete && ac.athlete.id === athlete.id;
        });

        characteristics.forEach(function(char) {
            const ac = acs.find(function(a) {
                return a.characteristic && a.characteristic.id === char.id;
            });
            const value = ac ? ac.value : 0;
            const percentage = Math.round((value / 100) * 100);

            const row = tbody.insertRow();
            row.innerHTML = '<td>' + athlete.name + '</td>' +
                '<td>' + char.name + '</td>' +
                '<td>' + value + '</td>' +
                '<td>' +
                '<div style="background: #e9ecef; border-radius: 10px; height: 20px; width: 100%;">' +
                '<div style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 10px; height: 100%; width: ' + percentage + '%; transition: width 0.5s;"></div>' +
                '</div>' +
                '<span style="font-size: 12px; color: #666;">' + percentage + '%</span>' +
                '</td>';
        });
    });
}