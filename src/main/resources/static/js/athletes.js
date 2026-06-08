document.addEventListener('DOMContentLoaded', async function() {
    await loadGroupsSelect();
    await loadAthletes();

    const form = document.getElementById('athleteForm');
    if (form) {
        form.addEventListener('submit', async function(e) {
            e.preventDefault();

            const name = document.getElementById('athleteName').value.trim();
            const birthDate = document.getElementById('athleteBirthDate').value;
            const groupId = document.getElementById('athleteGroup').value;

            if (!name || !groupId) {
                alert('Заполните все поля!');
                return;
            }

            const result = await createAthlete(name, birthDate, groupId);
            if (result) {
                alert('Спортсмен добавлен!');
                document.getElementById('athleteName').value = '';
                document.getElementById('athleteBirthDate').value = '';
                await loadAthletes();
            } else {
                alert('Ошибка добавления');
            }
        });
    }
});

async function loadGroupsSelect() {
    const groups = await getGroups();
    const select = document.getElementById('athleteGroup');
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

async function loadAthletes() {
    const athletes = await getAthletes();
    const container = document.getElementById('athletesList');

    if (!container) return;

    if (athletes.length === 0) {
        container.innerHTML = '<p style="color: #666; text-align: center; padding: 20px;">Нет спортсменов. Добавьте первого!</p>';
        return;
    }

    const groups = await getGroups();
    const groupsMap = {};
    groups.forEach(function(g) {
        groupsMap[g.id] = g.name;
    });

    container.innerHTML = athletes.map(function(athlete) {
        let groupName = 'Не назначен';

        if (athlete.group && athlete.group.name) {
            groupName = athlete.group.name;
        } else if (athlete.groupId && groupsMap[athlete.groupId]) {
            groupName = groupsMap[athlete.groupId];
        } else if (athlete.group && athlete.group.id && groupsMap[athlete.group.id]) {
            groupName = groupsMap[athlete.group.id];
        }

        return '<div class="card">' +
            '<h3>' + athlete.name + '</h3>' +
            '<p>Дата рождения: ' + (athlete.birthDate || 'Не указана') + '</p>' +
            '<p>Группа: <strong>' + groupName + '</strong></p>' +
            '<button onclick="deleteAthleteById(' + athlete.id + ')" class="btn" style="background: #ff6b6b;">Удалить</button>' +
            '</div>';
    }).join('');
}

async function deleteAthleteById(id) {
    if (!confirm('Удалить спортсмена?')) return;

    const result = await deleteAthlete(id);
    if (result) {
        alert('Спортсмен удалён');
        await loadAthletes();
    } else {
        alert('Ошибка удаления');
    }
}