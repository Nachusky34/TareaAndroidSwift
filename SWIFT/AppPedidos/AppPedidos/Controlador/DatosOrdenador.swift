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
        fatalError("Failed to load ordenadires.json from the bundle.")
    }
    
    do {
        let decodedData = try JSONDecoder().decode([String: [Ordenador]].self, from: data)
        if let ordenador = decodedData["ordenadores"] {
            return ordenador
        } else {
            fatalError("Key 'ordenador' not found.")
        }
    } catch {
        fatalError("Failed to decode JSON: \(error)")
    }
}
