var API_BASE = '/api';

function getGroups() {
    return fetch(API_BASE + '/groups')
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка загрузки групп');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return [];
        });
}

// ← ЭТА ФУНКЦИЯ БЫЛА ОТСУТСТВУЕТ! ДОБАВЛЯЕМ!
function getGroupById(id) {
    return fetch(API_BASE + '/groups/' + id)
        .then(function(response) {
            if (!response.ok) throw new Error('Группа не найдена');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return null;
        });
}

function createGroup(name, description) {
    return fetch(API_BASE + '/groups', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            name: name,
            description: description,
            createdAt: new Date().toISOString()
        })
    })
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка создания');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return null;
        });
}

function deleteGroup(id) {
    return fetch(API_BASE + '/groups/' + id, {
        method: 'DELETE'
    })
        .then(function(response) {
            return response.ok;
        })
        .catch(function(error) {
            console.error('Error:', error);
            return false;
        });
}

function getAthletes() {
    return fetch(API_BASE + '/athletes')
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return [];
        });
}

function getAthleteById(id) {
    return fetch(API_BASE + '/athletes/' + id)
        .then(function(response) {
            if (!response.ok) throw new Error('Спортсмен не найден');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return null;
        });
}

async function createAthlete(name, birthDate, groupId) {
    try {
        const response = await fetch(API_BASE + '/athletes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                name: name,
                birthDate: birthDate,
                group: { id: parseInt(groupId) }
            })
        });
        if (!response.ok) throw new Error('Ошибка создания спортсмена');
        return await response.json();
    } catch (error) {
        console.error('Error creating athlete:', error);
        return null;
    }
}

function deleteAthlete(id) {
    return fetch(API_BASE + '/athletes/' + id, {
        method: 'DELETE'
    })
        .then(function(response) {
            return response.ok;
        })
        .catch(function(error) {
            console.error('Error:', error);
            return false;
        });
}

function getCharacteristics() {
    return fetch(API_BASE + '/characteristics')
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return [];
        });
}

function getCharacteristicById(id) {
    return fetch(API_BASE + '/characteristics/' + id)
        .then(function(response) {
            if (!response.ok) throw new Error('Характеристика не найдена');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return null;
        });
}

function createCharacteristic(name, description, groupId) {
    return fetch(API_BASE + '/characteristics', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            name: name,
            description: description,
            group: { id: groupId }
        })
    })
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return null;
        });
}

function deleteCharacteristic(id) {
    return fetch(API_BASE + '/characteristics/' + id, {
        method: 'DELETE'
    })
        .then(function(response) {
            return response.ok;
        })
        .catch(function(error) {
            console.error('Error:', error);
            return false;
        });
}

function getAthleteCharacteristics() {
    return fetch(API_BASE + '/athlete-characteristics')
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return [];
        });
}

function createAthleteCharacteristic(athleteId, characteristicId, value) {
    return fetch(API_BASE + '/athlete-characteristics', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            athlete: { id: athleteId },
            characteristic: { id: characteristicId },
            value: value
        })
    })
        .then(function(response) {
            if (!response.ok) throw new Error('Ошибка');
            return response.json();
        })
        .catch(function(error) {
            console.error('Error:', error);
            return null;
        });
}