import Foundation

func CargarDatosCompra() -> [Compra] {
    let fileURL = obtenerURLArchivo()
    print("Ruta del archivo JSON: \(fileURL.path)")
    
    // Si el archivo no existe, lo copia desde el bundle
    if !FileManager.default.fileExists(atPath: fileURL.path) {
        if let bundleURL = Bundle.main.url(forResource: "compras", withExtension: "json") {
            do {
                try FileManager.default.copyItem(at: bundleURL, to: fileURL)
                print("Archivo copiado desde el bundle.")
            } catch {
                print("Error copiando el archivo JSON: \(error)")
            }
        } else {
            print("No se encontró el archivo en el bundle.")
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
            print("Clave 'compra' no encontrada.")
            return []
        }
    } catch {
        print("Error al cargar el JSON: \(error)")
        return []
    }
}

func guardarCompras(compras: [Compra]) {
    let fileURL = obtenerURLArchivo()
    
    do {
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        let data = try encoder.encode(["compra": compras])
        try data.write(to: fileURL)
        print("Compras guardadas correctamente.")
    } catch {
        print("Error al guardar el JSON: \(error)")
    }
}

func agregarCompra(compras: inout [Compra], idUsuario: Int, idProducto: Int, cantidad: Int) {
    let nuevaCompra = Compra(
        idUsuario: idUsuario,
        idProducto: idProducto,
        fecha: obtenerFecha(),
        hora: obtenerHora(),
        cantidad: cantidad,
        comprado: false
    )
    
    compras.append(nuevaCompra)
    guardarCompras(compras: compras)
    print("Compra agregada.")
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

func obtenerURLArchivo() -> URL {
    let fileManager = FileManager.default
    let urls = fileManager.urls(for: .documentDirectory, in: .userDomainMask)
    let documentsDirectory = urls[0]
    return documentsDirectory.appendingPathComponent("compras.json")
}

