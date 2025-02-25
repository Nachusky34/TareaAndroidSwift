//
//  Tienda.swift
//  AppPedidos
//
//  Created by Usuario invitado on 25/2/25.
//

import SwiftUI

struct Tienda: View {
    
    @State private var ordenadores: [Ordenador] = [
        Ordenador(id: 1, nombre: "Ordenador 1", descripcion: "Descripción aleatoria...", precio: 1249.95, img: "ordenador1"),
        Ordenador(id: 2, nombre: "Ordenador 2", descripcion: "Otro ordenador...", precio: 999.99, img: "ordenador2"),
        Ordenador(id: 3, nombre: "Ordenador 3", descripcion: "Más potente aún...", precio: 1499.99, img: "ordenador3")
    ]
    
    @State private var ordenadorSeleccionado: Ordenador?
    
    var body: some View {
        NavigationView {
            List(ordenadores) { ordenador in
                Button(action: {
                    ordenadorSeleccionado = ordenador
                }) {
                    HStack {
                        Image(ordenador.img)
                            .resizable()
                            .frame(width: 50, height: 50)
                            .cornerRadius(8)
                            .padding()
                        
                        VStack(alignment: .leading) {
                            Text(ordenador.nombre)
                                .font(.custom("Times New Roman", size: 30))
                                .foregroundColor(.black)
                            
                            Text(String(format: "%.2f$", ordenador.precio))
                                .font(.custom("Times New Roman", size: 30))
                                .foregroundColor(.gray)
                                .padding()
                                .padding(.leading, 60)
                            
                        }
                        
                        Spacer()
                    }
                    .padding(8)
                }
            }
            .navigationTitle("Tienda")
    
        }
        .sheet(item: $ordenadorSeleccionado) { ordenador in
            VistaProducto(ordenador : ordenador)
        }
    }
}

#Preview {
    Tienda()
}
