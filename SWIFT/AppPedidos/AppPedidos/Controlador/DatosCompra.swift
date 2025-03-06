import Foundation

func CargarDatosCompra() -> [Compra] {
    let fileURL = obtenerURLArchivo()
    print("Ruta del archivo JSON en el contenedor: \(fileURL.path)")
    
    // Si el archivo no existe, lo copia desde la carpeta Jsons dentro del bundle
    if !FileManager.default.fileExists(atPath: fileURL.path) {
        if let bundleURL = Bundle.main.url(forResource: "compras", withExtension: "json", subdirectory: "Jsons") {
            do {
                try FileManager.default.copyItem(at: bundleURL, to: fileURL)
                print("Archivo copiado desde la carpeta Jsons del bundle.")
            } catch {
                print("Error copiando el archivo JSON desde Jsons: \(error)")
            }
        } else {
            print("No se encontró el archivo 'compras.json' en la carpeta Jsons del bundle.")
        }
    }
    
    // Cargar datos desde el archivo
    do {
        let data = try Data(contentsOf: fileURL)
        let decoder = JSONDecoder()
        let decodedData = try decoder.decode([String: [Compra]].self, from: data)
        
        if let compras = decodedData["compra"] {
            print("Datos de compras cargados correctamente.")
            return compras
        } else {
            print("Clave 'compra' no encontrada en el archivo JSON.")
            return []
        }
    } catch {
        print("Error al cargar el JSON: \(error)")
        return []
    }
}

func guardarCompraJson(compra: Compra) {
    let fileURL = obtenerURLArchivo()
    
    do {
        // Cargar compras existentes
        var compras = CargarDatosCompra()
        
        compras.append(compra)
        
        // Guardar el array de compras con la nueva compra
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(["compra": compras])
        try data.write(to: fileURL)
        print("Compra guardada correctamente.")
    } catch {
        print("Error al guardar la compra: \(error)")
    }
}

func eliminarCompraJson(compra: Compra) {
    let fileURL = obtenerURLArchivo()
    
    do {
        var compras = CargarDatosCompra()
        
        compras.removeAll { $0.id == compra.id }
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(["compra": compras])
        try data.write(to: fileURL)
        
        print("Compra eliminada correctamente.")
    } catch {
        print("Error al eliminar la compra: \(error)")
    }
}

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

func actualizarCompra(ids: [String]) {
    let fileURL = obtenerURLArchivo()
    var compras = CargarDatosCompra()
    
    do {
        var compras = CargarDatosCompra()
        
        // Recorrer las compras
        for index in 0..<compras.count {
            if ids.contains(compras[index].id) {
                // Si el id de la compra está en el array `ids`, actualizar la propiedad `comprado` a true
                compras[index].comprado = true
            }
        }
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(["compra": compras])
        try data.write(to: fileURL)
    } catch {
        print("Error al hacer la compra: \(error)")
    }
}



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

