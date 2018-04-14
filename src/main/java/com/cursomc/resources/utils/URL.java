package com.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/** Classe Usada Para tratar os dados passados pela url
 * Ex: Converter uma vetor de string em uma lista de inteiros,
 *  faz o decode  da String
 */

public class URL {
	
	/** Função que faz a Decodeficação da String Passado na URL, caso seja passado espaço em branco
	 * EXEMPLO: "TV LED" encode fica "TV%20LED" O espaço é substituido por %20
	 */
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}	
	
	/** Função que converte um vetor de string, em uma lista de inteiros
	 * OBS: A Função split separa a string passada, com base no caracter que foi passado
	 * EXEMPLO: 1,2,3 
	 */
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		
		//Faz a mesmo coisa usando Lambda
		//return Arrays.asList(s.split(",")).stream().map(x->Integer.parseInt(x)).collect(Collectors.toList());
        
		
	}

}
