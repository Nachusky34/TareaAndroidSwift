//
//  Carrito.swift
//  AppPedidos
//
//  Created by Mario Seoane on 27/2/25.
//
import SwiftUI

struct Carrito: View {
    
    @State private var ordenadores: [Ordenador] = CargarDatosOrdenador()
    @State private var compras: [Compra] = CargarDatosCompra()
    @State private var ordenadorSeleccionado: Ordenador?
    
    var comprasFiltradas: [Compra] {
        compras.filter { $0.idUsuario == 1 && $0.comprado }
    }
    
    var total: Double {
        comprasFiltradas.reduce(into: 0.0) { acumulador, compra in
            if let ordenador = ordenadores.first(where: { $0.id == compra.idProducto }) {
                return acumulador += Double(compra.cantidad) * ordenador.precio
            }
            return acumulador
        }
    }
    
    var body: some View {
        NavigationView {
            VStack {
                List(comprasFiltradas) { compra in
                    if let ordenador = ordenadores.first(where: { $0.id == compra.idProducto }) {
                        HStack {
                            Image(ordenador.img)
                                .resizable()
                                .frame(width: 100, height: 100)
                                .cornerRadius(8)
                            
                            VStack(alignment: .leading) {
                                Text(ordenador.nombre)
                                    .font(.custom("Times New Roman", size: 30))
                                    .foregroundColor(.black)
                                
                                Text("Cantidad: \(compra.cantidad)")
                                    .font(.custom("Times New Roman", size: 20))
                                    .foregroundColor(.gray)
                                
                                Text(String(format: "%.2f$", ordenador.precio * (Double(compra.cantidad) ?? 0)))
                                    .font(.custom("Times New Roman", size: 25))
                                    .foregroundColor(.blue)
                                    .padding(.top, 5)
                            }
                            Spacer()
                        }
                        .padding(8)
                    }
                }
                
                Text("Total: \(String(format: "%.2f$", total))")
                    .font(.custom("Times New Roman", size: 30))
                    .foregroundColor(.blue)
                    .padding()
            }
            .navigationTitle("Carrito")
            .foregroundStyle(Color(red: 85/255, green: 183/255, blue: 232/255))
        }
    }
}

#Preview {
    Carrito()
}


