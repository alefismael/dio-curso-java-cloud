/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controle.de.fluxo;

import controle.de.fluxo.exceptions.ParametrosInvalidosException;

/**
 *
 * @author alefi
 */
public class Contador {

    public static void main(String[] args) {
		
		try {
			//chamando o método contendo a lógica de contagem
                    int parametroUm = Integer.parseInt(args[0]);
                    int parametroDois = Integer.parseInt(args[1]);
                        
                    contar(parametroUm, parametroDois);
                    
		}catch (ParametrosInvalidosException exception) {
			//imprimir a mensagem: O segundo parâmetro deve ser maior que o primeiro
                        System.out.println(exception.getMessage());
                        exception.getStackTrace();
		}
		
	}
	static void contar(int parametroUm, int parametroDois ) throws ParametrosInvalidosException {
		//validar os parâmetros e lança uma exceção caso seja necessário
	
                if (parametroDois - parametroUm < 0){
                    throw new ParametrosInvalidosException("O segundo parâmetro deve ser maior que o primeiro");
                }
                
		//realizar o for para imprimir os números com base na variável contagem
                System.out.println("Iniciando a contagem de "+parametroUm+" até "+parametroDois+".");
                for(int i = parametroUm; i <= parametroDois; i++){
                    System.out.println(i);
                }
                System.out.println("Contagem finalizada");
	}
    
}
