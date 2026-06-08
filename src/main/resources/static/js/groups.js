document.addEventListener('DOMContentLoaded', function() {
    loadGroups();

    const form = document.getElementById('groupForm');
    if (form) {
        form.addEventListener('submit', async function(e) {
            e.preventDefault();

            const name = document.getElementById('groupName').value.trim();
            const description = document.getElementById('groupDescription').value.trim();

            if (!name) {
                alert('Введите название группы!');
                return;
            }

            const result = await createGroup(name, description);
            if (result) {
                alert('Группа создана!');
                document.getElementById('groupName').value = '';
                document.getElementById('groupDescription').value = '';
                await loadGroups();
            } else {
                alert('Ошибка создания группы');
            }
        });
    }
});

async function loadGroups() {
    const groups = await getGroups();
    const container = document.getElementById('groupsList');

    if (!container) return;

    if (groups.length === 0) {
        container.innerHTML = '<p style="color: #666; text-align: center; padding: 20px;">Нет групп. Создайте первую!</p>';
        return;
    }

    container.innerHTML = groups.map(function(group) {
        return '<div class="card">' +
            '<h3>' + group.name + '</h3>' +
            '<p>' + (group.description || 'Нет описания') + '</p>' +
            '<p><small>Создана: ' + (group.createdAt ? new Date(group.createdAt).toLocaleDateString() : 'Неизвестно') + '</small></p>' +
            '<button onclick="deleteGroupById(' + group.id + ')" class="btn" style="background: #ff6b6b;">Удалить</button>' +
            '</div>';
    }).join('');
}

async function deleteGroupById(id) {
    if (!confirm('Удалить группу?')) return;

    const result = await deleteGroup(id);
    if (result) {
        alert('Группа удалена');
        await loadGroups();
    } else {
        alert('Ошибка удаления');
    }
}