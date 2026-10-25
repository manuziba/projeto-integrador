class MatrizesContato{
	double[][][] tabela;
	
	
	public double[][] mediaTabelas(){
		int turmas = tabela[0].length;
		int locais = tabela[0][0].length;
		int dias = tabela.length;
		double [][] matriz = new double[turmas][locais];
		
		
		for (int a = 0; a< tabela[0].length; a++){
			for (int b = 0; b < tabela[0][a].length; b++){
				double soma = 0;
				for (int c = 0; c<tabela.length; c++){
					soma = soma + tabela[c][a][b];
				}
				double mediaDias = soma/dias;
				matriz[a][b] = mediaDias;
			}
		}
		
		return matriz;
	}
	
	public static void main (String args[]){
		double[][][] tabela = { //TESTE
		{ // Dia 1
			{2, 4, 6},
			{1, 3, 5}
		},
		{ // Dia 2
			{4, 6, 8},
			{3, 5, 7}
		},
		{ // Dia 3
			{6, 8, 10},
			{5, 7, 9}
		}
		};
		MatrizesContato obj = new MatrizesContato();

		obj.tabela = tabela; 
		double[][] resultado = obj.mediaTabelas();
		for (int a = 0; a < resultado.length;a++){
			for (int b = 0; b<resultado[a].length; b++){
				System.out.println(resultado[a][b]);
			}
		}
	}
}
