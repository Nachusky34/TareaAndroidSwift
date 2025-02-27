//
//  Tienda.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//

import SwiftUI

struct VistaProducto: View {
    
    let ordenador: Ordenador
    
    @State private var cantidad: Int = 1
    
    var precioTotal: Double {
        return Double(cantidad) * ordenador.precio
        }
    
    var body: some View {
        VStack {
            
            Image(ordenador.img)

            VStack(alignment: .leading) {
                Text(ordenador.nombre)
                    .font(.custom("Times New Roman", size: 32))
                    .fontWeight(.bold)
                    .padding()
                
                Text(ordenador.descripcion)
                    .font(.custom("Times New Roman", size: 22))
            }
            .padding(.horizontal, 15)
            
            Spacer()
            
            HStack {
                // Sección de Precio
                HStack {
                    Text("Precio:")
                        .font(.custom("Times New Roman", size: 22))
                    Text(String(format: "%.2f$", ordenador.precio))
                        .font(.custom("Times New Roman", size: 22))
                }
                    .padding()
                    .border(Color.black)
                Spacer()
                            
                // Selector de Cantidad
                HStack(spacing: 0) {
                    Text("\(cantidad)")
                        .frame(width: 44, height: 56)
                        .multilineTextAlignment(.center)
                        .border(Color.black)
                                
                    VStack(spacing: 0) {
                        Button(action: { cantidad += 1 }) {
                            Text("+")
                                .frame(width: 33, height: 28)
                                .border(Color.black)
                        }
                        Button(action: { if cantidad > 1 { cantidad -= 1 } }) {
                            Text("-")
                                .frame(width: 33, height: 28)
                                .border(Color.black)
                        }
                    }
                }
            }
            .padding(.horizontal, 40)
            .padding(.bottom, 30)
            
            
            Button(action: {
                print("Producto agregado al carrito")
            }) {
                HStack {
                    Text("AGREGAR AL CARRITO")
                        .font(.custom("Times New Roman", size: 22))
                        .foregroundColor(.black)
                                
                    Spacer()
                                
                    Text(String(format: "%.2f$", precioTotal))
                        .font(.custom("Times New Roman", size: 22))
                        .foregroundColor(.black)
                    }
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color(red: 85/255, green: 183/255, blue: 232/255))
                    .cornerRadius(10)
                }
            .padding(.horizontal, 40)
            .padding(.bottom, 40)
        }
        
        
        .padding()
    }
}

#Preview {
    VistaProducto(ordenador: Ordenador(id: 1, nombre: "PC Ejemplo", descripcion: "mas ejemplo", precio: 1500.0, img: "Ordenador1"))
}

