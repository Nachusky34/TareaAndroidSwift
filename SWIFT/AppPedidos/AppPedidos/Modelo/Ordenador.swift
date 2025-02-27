//
//  Ordenador.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

struct Ordenador: Codable, Identifiable {
    let id: Int
    let nombre: String
    let descripcion: String
    let precio: Double
    let img: String
    
    
    init(id: Int, nombre: String, descripcion: String, precio: Double, img: String) {
        self.id = id
        self.nombre = nombre
        self.descripcion = descripcion
        self.precio = precio
        self.img = img
    }

    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        if let idString = try? container.decode(String.self, forKey: .id) {
            self.id = Int(idString) ?? 0
        } else {
            self.id = try container.decode(Int.self, forKey: .id)
        }
        self.nombre = try container.decode(String.self, forKey: .nombre)
        self.descripcion = try container.decode(String.self, forKey: .descripcion)
        
        let precioString = try container.decode(String.self, forKey: .precio)
        self.precio = Double(precioString) ?? 0.0
        
        self.img = try container.decode(String.self, forKey: .img)
    }
}
