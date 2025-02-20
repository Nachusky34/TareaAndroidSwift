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
        let decodedData = try JSONDecoder().decode([String: [Usuario]].self, from: data)
        if let usuarios = decodedData["usuarios"] {
            return usuarios
        } else {
            fatalError("Key 'usuarios' not found.")
        }
    } catch {
        fatalError("Failed to decode JSON: \(error)")
    }
}
