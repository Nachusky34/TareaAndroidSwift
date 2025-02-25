//
//  DatosUsuario.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

func CargarDatosUsuario() -> [Usuario] {
    guard let url = Bundle.main.url(forResource: "usuarios", withExtension: "json"),
          let data = try? Data(contentsOf: url) else {
        fatalError("Failed to load usuarios.json from the bundle.")
    }

    do {
        // Decode the JSON as a dictionary with user identifiers as keys and Usuario objects as values.
        let decodedData = try JSONDecoder().decode([String: [String: Usuario]].self, from: data)
        
        // Extract the "usuario" key and flatten the user dictionary into an array.
        if let usuarios = decodedData["usuario"]?.values {
            return Array(usuarios) // Convert the dictionary values to an array
        } else {
            fatalError("Key 'usuario' not found.")
        }
    } catch {
        fatalError("Failed to decode JSON: \(error)")
    }
}

