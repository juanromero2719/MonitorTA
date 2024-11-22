// Obtener el parámetro ID de la URL
const params = new URLSearchParams(window.location.search);
const id = params.get("id");

async function fetchDetalleLog() {
    const container = document.getElementById("data-container");

    container.innerHTML = `<tr><td colspan="2">Cargando...</td></tr>`;

    try {

        const response = await fetch(`/api/logs/${id}`);
        console.log("INICIANDO TRY!");
        
        
        if (!response.ok) {
            console.log("ERROR!");
            throw new Error(`Error: ${response.status} ${response.statusText}`);
        }

        // Parsear la respuesta a JSON
        const log = await response.json();
        console.log("PARSEANDO RESPUESTA!");
        
        // Renderizar los detalles del registro
        container.innerHTML = `
            <tr>
                <th>ID Registro</th>
                <td>${log.idRegistro}</td>
            </tr>
            <tr>
                <th>Usuario</th>
                <td>${log.usuario.nombre || 'No disponible'}</td>
            </tr>
            <tr>
                <th>Dirección IP</th>
                <td>${log.direccionIP || 'No disponible'}</td>
            </tr>
            <tr>
                <th>Nombre Archivo</th>
                <td>${log.nombreArchivo || 'No disponible'}</td>
            </tr>
            <tr>
                <th>Contenido</th>
                <td>${log.contenido || 'No disponible'}</td>
            </tr>
            <tr>
                <th>Fecha de Subida</th>
                <td>${log.fechaSubida || 'No disponible'}</td>
            </tr>
        `;
    } catch (error) {
        
        container.innerHTML = `
            <tr>
                <td colspan="2">Error al cargar los datos: ${error.message}</td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <button onclick="fetchDetalleLog()">Reintentar</button>
                </td>
            </tr>
        `;
    }
}

// Llamar a la función cuando la página esté cargada
document.addEventListener("DOMContentLoaded", fetchDetalleLog);
