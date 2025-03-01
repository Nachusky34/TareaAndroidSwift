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
        // Decodificar el JSON como un diccionario de usuarios
        let decodedData = try JSONDecoder().decode([String: [String: Usuario]].self, from: data)

        // Extraer los valores de la clave "usuario" y convertirlo en un array
        if let usuarios = decodedData["usuario"]?.values {
            return Array(usuarios) // Convertir los valores del diccionario en un array
        } else {
            fatalError("Key 'usuario' not found.")
        }
    } catch {
        fatalError("Failed to decode JSON: \(error)")
    }
}
