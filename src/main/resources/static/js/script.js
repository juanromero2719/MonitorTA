
    fetch('/api/user-logs')
        .then(response => response.json())
        .then(data => {
            const dataContainer = document.getElementById('data-container');
            dataContainer.innerHTML = ''; // Limpiar contenido existente

            if (data.length === 0) {
                dataContainer.innerHTML = '<tr><td colspan="2">No se encontraron registros.</td></tr>';
            } else {
                data.forEach(log => {
                    const row = document.createElement('tr');
                    const ipCell = document.createElement('td');
                    const dateCell = document.createElement('td');

                    const parts = log.split('/');
                    const archivo = parts[parts.length - 1]; // Nombre del archivo

                    const archivoParts = archivo.replace('registro_', '').replace('.txt', '').split('_');
                    const ip = archivoParts[0]; // Primera parte después de "registro_"
                    const fecha = archivoParts.slice(1).join(' '); // Resto de las partes como fecha y hora

                    ipCell.textContent = ip;

                    const rutaNormalizada = log.replace(/\\/g, '/').replace(/\/logs\/logs\//, '/logs/');
                    const link = document.createElement('a');
                    link.href = `/log-detalle?archivo=${encodeURIComponent(rutaNormalizada)}`;
                    link.textContent = fecha;
                    dateCell.appendChild(link);

                    row.appendChild(ipCell);
                    row.appendChild(dateCell);
                    dataContainer.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error('Error al obtener los registros:', error);
            document.getElementById('data-container').innerHTML =
                '<tr><td colspan="2">Error al cargar los registros.</td></tr>';
        });
