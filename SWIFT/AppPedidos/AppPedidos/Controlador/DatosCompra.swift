//
//  Created by Ignacio bermejo on 20/2/25.
//


import SwiftUI
import Foundation

class GestorDatos: ObservableObject {
    @Published var compra: [Compra] = []
    
    private let compraJson = "compras.json"

    init() {
        cargarJSON()
    }
    
    // Obtiene la URL del archivo en el directorio de documentos
    private func obtenerDirectorioDocumentos() -> URL {
        FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0]
    }
    
    // Obtiene la ruta completa del archivo JSON en el directorio de documentos
    private func obtenerURLArchivo() -> URL {
        obtenerDirectorioDocumentos().appendingPathComponent(compraJson)
    }
    
    // Carga el JSON (si no existe, lo copia desde el bundle)
    func cargarJSON() {
        let fileURL = obtenerURLArchivo()
        print("Ruta del archivo JSON: \(fileURL.path)")
        
        if !FileManager.default.fileExists(atPath: fileURL.path) {
            // Copiar el archivo desde el bundle si no existe
            if let bundleURL = Bundle.main.url(forResource: "datos", withExtension: "json") {
                do {
                    try FileManager.default.copyItem(at: bundleURL, to: fileURL)
                } catch {
                    print("Error copiando el JSON desde el bundle: \(error)")
                }
            }
        }
        
        do {
            let data = try Data(contentsOf: fileURL)
            let decoder = JSONDecoder()
            self.compra = try decoder.decode([Compra].self, from: data)
        } catch {
            print("Error al cargar el JSON: \(error)")
        }
    }
    
    // Guarda los datos en el JSON
    func salvarJSON() {
        let fileURL = obtenerURLArchivo()
        do {
            let encoder = JSONEncoder()
            encoder.outputFormatting = .prettyPrinted
            let dataCompra = try encoder.encode(compra)
            try dataCompra.write(to: fileURL, options: .atomicWrite)
        } catch {
            print("Error al guardar el JSON: \(error)")
        }
    }
    
    // Agrega una nueva persona y guarda los cambios
    func agregarCompra(idUsuario: Int, idProducto: Int) {
        let newCompra = Compra(idUsuario: <#T##Int#>, idProducto: <#T##Int#>, fecha: obtenerDia(), hora: obtenerHora(), cantidad: <#T##Int#>)
        compra.append(newCompra)
        salvarJSON()
    }
    
    func obtenerDia() -> String{
        let currentDate = Date()
        let calendar = Calendar.current
        let components = calendar.dateComponents([.day, .month, .year], from: currentDate)

        if let day = components.day, let month = components.month, let year = components.year {
            return("Día: /(day), Mes: /(month), Año: /(year)")
        }
    }
    
    func obtenerHora() -> String{
        let currentDate = Date()
        let calendar = Calendar.current
        let components = calendar.dateComponents([.hour, .minute], from: currentDate)

        if let hour = components.hour, let minute = components.minute {
            return("Hora: /(hour):/(minute)")
        }
    }
}
