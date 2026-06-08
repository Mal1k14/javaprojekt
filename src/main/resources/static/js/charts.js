let groupRadarChart = null;
let comparisonChart = null;

// Создать паукообразную диаграмму для средних показателей группы
function createGroupRadarChart(labels, data, label = 'Средние показатели') {
    const ctx = document.getElementById('groupRadarChart');
    if (!ctx) return;

    if (groupRadarChart) {
        groupRadarChart.destroy();
    }

    groupRadarChart = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                backgroundColor: 'rgba(102, 126, 234, 0.2)',
                borderColor: 'rgba(102, 126, 234, 1)',
                pointBackgroundColor: 'rgba(102, 126, 234, 1)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgba(102, 126, 234, 1)',
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            scales: {
                r: {
                    beginAtZero: true,
                    max: 100,
                    ticks: { stepSize: 20 }
                }
            },
            plugins: {
                legend: { position: 'top' }
            }
        }
    });
}

// Создать диаграмму сравнения спортсменов
function createComparisonChart(labels, datasets) {
    const ctx = document.getElementById('athletesComparisonChart');
    if (!ctx) return;

    if (comparisonChart) {
        comparisonChart.destroy();
    }

    const colors = [
        { bg: 'rgba(102, 126, 234, 0.2)', border: 'rgba(102, 126, 234, 1)' },
        { bg: 'rgba(234, 102, 102, 0.2)', border: 'rgba(234, 102, 102, 1)' },
        { bg: 'rgba(102, 234, 150, 0.2)', border: 'rgba(102, 234, 150, 1)' },
        { bg: 'rgba(234, 200, 102, 0.2)', border: 'rgba(234, 200, 102, 1)' },
        { bg: 'rgba(180, 102, 234, 0.2)', border: 'rgba(180, 102, 234, 1)' }
    ];

    const coloredDatasets = datasets.map((ds, i) => ({
        ...ds,
        backgroundColor: colors[i % colors.length].bg,
        borderColor: colors[i % colors.length].border,
        pointBackgroundColor: colors[i % colors.length].border,
        borderWidth: 2
    }));

    comparisonChart = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: labels,
            datasets: coloredDatasets
        },
        options: {
            responsive: true,
            scales: {
                r: {
                    beginAtZero: true,
                    max: 100,
                    ticks: { stepSize: 20 }
                }
            },
            plugins: {
                legend: { position: 'top' }
            }
        }
    });
}

// Рассчитать средние показатели группы
function calculateGroupAverages(characteristics, athleteCharacteristics) {
    const averages = {};

    characteristics.forEach(char => {
        const values = athleteCharacteristics
            .filter(ac => ac.characteristic && ac.characteristic.id === char.id)
            .map(ac => ac.value);

        if (values.length > 0) {
            averages[char.name] = Math.round(values.reduce((a, b) => a + b, 0) / values.length);
        } else {
            averages[char.name] = 0;
        }
    });

    return averages;
}