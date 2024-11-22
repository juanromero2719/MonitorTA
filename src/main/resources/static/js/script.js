// Función para cargar los datos mediante AJAX
async function fetchLogs() {
    const container = document.getElementById("data-container");

    // Mostrar un estado de "cargando" inicialmente
    container.innerHTML = `<tr><td colspan="3">Cargando...</td></tr>`;

    try {
        // Realizar la petición a tu endpoint
        const response = await fetch('/api/logs');

        // Manejar respuestas que no son 200 OK
        if (!response.ok) {
            throw new Error(`Error: ${response.status} ${response.statusText}`);
        }

        // Parsear los datos a JSON
        const logs = await response.json();

        // Renderizar los datos
        container.innerHTML = logs.map(log => `
            <tr>
                <td>${log.usuario ? log.usuario.nombre : 'Sin Usuario'}</td>
                <td>${log.direccionIP || 'N/A'}</td>
                <td>
                    <a href="/log-detalle?id=${log.idRegistro}">${log.fechaSubida}</a>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        // Mostrar un mensaje de error en la tabla
        container.innerHTML = `<tr><td colspan="3">Error al cargar los datos: ${error.message}</td></tr>`;
    }
}

// Llamar a la función después de que la página se haya cargado
document.addEventListener("DOMContentLoaded", fetchLogs);
