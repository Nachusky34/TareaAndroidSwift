import Foundation

// Función para cargar los datos de compras desde el archivo JSON
func CargarDatosCompra() -> [Compra] {
    let fileURL = obtenerURLArchivo()
    print("Ruta del archivo JSON en el contenedor: \(fileURL.path)")
    
    // Si el archivo no existe, lo crea por primera vez
    if !FileManager.default.fileExists(atPath: fileURL.path) {
        print("El archivo no existe, creando un nuevo JSON vacío.")
        guardarEstructuraInicialCompra()
    }

    // Validar si el JSON es correcto antes de cargarlo
    if !validarJSONCompra() {
        print("Error: JSON corrupto. Se restablecerá el archivo.")
        guardarEstructuraInicialCompra() // Solo se reinicia si es inválido
    }
    
    // Intentar cargar los datos desde el JSON
    do {
        let data = try Data(contentsOf: fileURL)
        let decoder = JSONDecoder()
        let decodedData = try decoder.decode(DatosCompra.self, from: data)
        print("Datos de compras cargados correctamente.")
        return decodedData.compras
    } catch {
        print("Error al cargar el JSON: \(error)")
        return []
    }
}

// Estructura del JSON con clave "compras"
struct DatosCompra: Codable {
    var compras: [Compra]
}

// Función para crear el JSON solo la primera vez
func guardarEstructuraInicialCompra() {
    let fileURL = obtenerURLArchivo()
    let datosIniciales = DatosCompra(compras: [])  // Objeto con array vacío
    
    do {
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(datosIniciales)
        try data.write(to: fileURL)
        print("Archivo 'compras.json' creado correctamente con clave 'compras'.")
    } catch {
        print("Error al crear el archivo JSON vacío: \(error)")
    }
}

// Función para validar si el JSON contiene la clave correcta antes de cargarlo
func validarJSONCompra() -> Bool {
    let fileURL = obtenerURLArchivo()
    
    do {
        let data = try Data(contentsOf: fileURL)
        let json = try JSONSerialization.jsonObject(with: data, options: [])
        
        if let dict = json as? [String: Any], dict["compras"] != nil {
            return true // El JSON es válido
        } else {
            return false // Falta la clave "compras"
        }
    } catch {
        return false // Error al leer el JSON (puede estar vacío o dañado)
    }
}

// Función para guardar una compra en el JSON
func guardarCompraJson(compra: Compra) {
    let fileURL = obtenerURLArchivo()
    
    do {
        // Cargar compras existentes
        var compras = CargarDatosCompra()
        
        compras.append(compra)
        
        // Guardar el array de compras con la nueva compra
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(DatosCompra(compras: compras)) // Ahora usa "compras"
        try data.write(to: fileURL)
        print("Compra guardada correctamente.")
    } catch {
        print("Error al guardar la compra: \(error)")
    }
}

// Función para eliminar una compra del JSON
func eliminarCompraJson(compra: Compra) {
    let fileURL = obtenerURLArchivo()
    
    do {
        var compras = CargarDatosCompra()
        
        compras.removeAll { $0.id == compra.id }
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(DatosCompra(compras: compras)) // Usa "compras"
        try data.write(to: fileURL)
        
        print("Compra eliminada correctamente.")
    } catch {
        print("Error al eliminar la compra: \(error)")
    }
}

// Función para agregar una nueva compra
func agregarCompra(idUsuario: Int, idProducto: Int, cantidad: Int) {
    let nuevaCompra = Compra(
        id: "\(idUsuario)\(idProducto)\(obtenerMilisegundos())",
        idUsuario: idUsuario,
        idProducto: idProducto,
        fecha: obtenerFecha(),
        hora: obtenerHora(),
        cantidad: cantidad,
        comprado: false
    )
    
    guardarCompraJson(compra: nuevaCompra)
    print("Compra agregada.")
}

// Función para actualizar el estado de compra de varios productos
func actualizarCompra(ids: [String]) {
    let fileURL = obtenerURLArchivo()
    var compras = CargarDatosCompra() // Se carga una sola vez

    do {
        // Marcar las compras como "compradas"
        for index in 0..<compras.count {
            if ids.contains(compras[index].id) {
                compras[index].comprado = true
            }
        }
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(DatosCompra(compras: compras)) // Usa "compras"
        try data.write(to: fileURL)
        
        print("Estado de compra actualizado correctamente.")
    } catch {
        print("Error al hacer la compra: \(error)")
    }
}

// Funciones auxiliares
func obtenerFecha() -> String {
    let formato = DateFormatter()
    formato.dateFormat = "dd/MM/yyyy"
    return formato.string(from: Date())
}

func obtenerHora() -> String {
    let formato = DateFormatter()
    formato.dateFormat = "HH:mm"
    return formato.string(from: Date())
}

func obtenerMilisegundos() -> String {
    return String(Int(Date().timeIntervalSince1970 * 1000))
}

func obtenerURLArchivo() -> URL {
    let fileManager = FileManager.default
    let urls = fileManager.urls(for: .documentDirectory, in: .userDomainMask)
    let documentsDirectory = urls[0]
    return documentsDirectory.appendingPathComponent("compras.json")
}
