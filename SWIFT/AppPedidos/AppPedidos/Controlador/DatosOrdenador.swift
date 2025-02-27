//
//  DatosOrdenador.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

func CargarDatosOrdenador() -> [Ordenador] {
    guard let url = Bundle.main.url(forResource: "ordenadores", withExtension: "json"),
          let data = try? Data(contentsOf: url) else {
        fatalError("Failed to load ordenadores.json from the bundle.")
    }
    
    do {
        let decodedData = try JSONDecoder().decode([String: [String: Ordenador]].self, from: data)
        if let productos = decodedData["producto"]?.values {
            return Array(productos)
        } else {
            fatalError("Key 'producto' not found.")
        }
    } catch {
        fatalError("Failed to decode JSON: \(error)")
    }
}
