//
//  DatosUsuario.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

func CargarDatosUsuario() -> [Usuario] {
    let fileURL = obtenerURLArchivoUsuarios() // URL del archivo en el dispositivo

    // Intentar leer desde el archivo local si existe
    if FileManager.default.fileExists(atPath: fileURL.path) {
        do {
            let data = try Data(contentsOf: fileURL)
            return decodificarUsuariosDesdeJSON(data: data)
        } catch {
            print("Error al leer el archivo usuarios.json desde el almacenamiento del dispositivo: \(error)")
        }
    }

    // Si el archivo local no existe, intenta cargarlo desde el bundle
    guard let bundleURL = Bundle.main.url(forResource: "usuarios", withExtension: "json"),
          let bundleData = try? Data(contentsOf: bundleURL) else {
        fatalError("Failed to load usuarios.json from the bundle.")
    }

    return decodificarUsuariosDesdeJSON(data: bundleData)
}

func decodificarUsuariosDesdeJSON(data: Data) -> [Usuario] {
    do {
        let decodedData = try JSONDecoder().decode([String: [String: Usuario]].self, from: data)

        if let usuarios = decodedData["usuario"]?.values {
            return Array(usuarios) // Convertir los valores del diccionario en un array
        } else {
            print("Error: Key 'usuario' no encontrada en el JSON.")
            return []
        }
    } catch {
        print("Error al decodificar JSON: \(error)")
        return []
    }
}


func guardarCambiosUsuario(usuario: Usuario) {
    let fileURL = obtenerURLArchivoUsuarios()

    var usuarios = CargarDatosUsuario()

    if let index = usuarios.firstIndex(where: { $0.id == usuario.id }) {
        usuarios[index].email = usuario.email
        usuarios[index].postalcode = usuario.postalcode
        usuarios[index].newsletter = usuario.newsletter

        do {
            let encoder = JSONEncoder()
            encoder.outputFormatting = .prettyPrinted

            // Convertir id a String para usarlo como clave en el diccionario
            let usuariosDict = Dictionary(uniqueKeysWithValues: usuarios.map { ("\($0.id)", $0) })
            let data = try encoder.encode(["usuario": usuariosDict])

            try data.write(to: fileURL)
            print("Usuario actualizado correctamente en usuarios.json.")
            print(fileURL.path)
        } catch {
            print("Error al guardar cambios: \(error)")
        }
    } else {
        print("Usuario no encontrado.")
    }
}


// Función para obtener la URL del archivo JSON de usuarios
func obtenerURLArchivoUsuarios() -> URL {
    let fileManager = FileManager.default
    let urls = fileManager.urls(for: .documentDirectory, in: .userDomainMask)
    return urls[0].appendingPathComponent("usuarios.json")
}

