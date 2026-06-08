document.addEventListener('DOMContentLoaded', async function() {
    await loadGroupsSelect();
    await loadAthletesSelect();
    await loadCharacteristicsSelect();
    await loadCharacteristics();
    await loadAthleteCharacteristics();

    // Форма создания характеристики
    const charForm = document.getElementById('characteristicForm');
    if (charForm) {
        charForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const name = document.getElementById('charName').value.trim();
            const description = document.getElementById('charDescription').value.trim();
            const groupId = document.getElementById('charGroup').value;

            if (!name || !groupId) {
                alert('Заполните все поля!');
                return;
            }

            const result = await createCharacteristic(name, description, groupId);
            if (result) {
                alert('Характеристика добавлена!');
                document.getElementById('charName').value = '';
                document.getElementById('charDescription').value = '';
                await loadCharacteristics();
                await loadCharacteristicsSelect();
            } else {
                alert('Ошибка добавления');
            }
        });
    }

    // Форма добавления значения характеристики спортсмену
    const acForm = document.getElementById('athleteCharForm');
    if (acForm) {
        acForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const athleteId = document.getElementById('acAthlete').value;
            const characteristicId = document.getElementById('acCharacteristic').value;
            const value = parseInt(document.getElementById('acValue').value);

            if (!athleteId || !characteristicId || isNaN(value)) {
                alert('Заполните все поля!');
                return;
            }

            if (value < 0 || value > 100) {
                alert('Значение должно быть от 0 до 100!');
                return;
            }

            const result = await createAthleteCharacteristic(
                parseInt(athleteId),
                parseInt(characteristicId),
                value
            );

            if (result) {
                alert('Значение добавлено!');
                document.getElementById('acValue').value = '';
                await loadAthleteCharacteristics();
            } else {
                alert('Ошибка добавления');
            }
        });
    }
});

async function loadGroupsSelect() {
    const groups = await getGroups();
    const select = document.getElementById('charGroup');
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

async function loadAthletesSelect() {
    const athletes = await getAthletes();
    const select = document.getElementById('acAthlete');
    if (!select) return;

    if (athletes.length === 0) {
        select.innerHTML = '<option value="">Нет спортсменов</option>';
        return;
    }

    select.innerHTML = '<option value="">Выберите спортсмена</option>' +
        athletes.map(function(a) {
            return '<option value="' + a.id + '">' + a.name + '</option>';
        }).join('');
}

async function loadCharacteristicsSelect() {
    const characteristics = await getCharacteristics();
    const select = document.getElementById('acCharacteristic');
    if (!select) return;

    if (characteristics.length === 0) {
        select.innerHTML = '<option value="">Нет характеристик</option>';
        return;
    }

    select.innerHTML = '<option value="">Выберите характеристику</option>' +
        characteristics.map(function(c) {
            return '<option value="' + c.id + '">' + c.name + '</option>';
        }).join('');
}

async function loadCharacteristics() {
    const characteristics = await getCharacteristics();
    const container = document.getElementById('characteristicsList');

    if (!container) return;

    if (characteristics.length === 0) {
        container.innerHTML = '<p style="color: #666; text-align: center; padding: 20px;">Нет характеристик</p>';
        return;
    }

    container.innerHTML = characteristics.map(function(char) {
        return '<div class="card">' +
            '<h3>' + char.name + '</h3>' +
            '<p>' + (char.description || 'Нет описания') + '</p>' +
            '<p>Группа: ' + (char.group ? char.group.name : 'Не указана') + '</p>' +
            '<button onclick="deleteCharacteristicById(' + char.id + ')" class="btn" style="background: #ff6b6b;">Удалить</button>' +
            '</div>';
    }).join('');
}

async function loadAthleteCharacteristics() {
    const athleteCharacteristics = await getAthleteCharacteristics();
    const container = document.getElementById('athleteCharacteristicsList');

    if (!container) return;

    if (athleteCharacteristics.length === 0) {
        container.innerHTML = '<p style="color: #666; text-align: center; padding: 20px;">Нет значений характеристик</p>';
        return;
    }

    container.innerHTML = athleteCharacteristics.map(function(ac) {
        const athleteName = ac.athlete ? ac.athlete.name : 'Неизвестно';
        const charName = ac.characteristic ? ac.characteristic.name : 'Неизвестно';

        return '<div class="card">' +
            '<h3>' + athleteName + ' - ' + charName + '</h3>' +
            '<p>Значение: <strong>' + ac.value + '</strong></p>' +
            '</div>';
    }).join('');
}

async function deleteCharacteristicById(id) {
    if (!confirm('Удалить характеристику?')) return;

    const result = await deleteCharacteristic(id);
    if (result) {
        alert('Характеристика удалена');
        await loadCharacteristics();
        await loadCharacteristicsSelect();
    } else {
        alert('Ошибка удаления');
    }
}