class MatrizesContato {
    double[][][] tabela;
    int turmas;
    int locais;
    int dias;
    double[] tamanhoTurma;
    double fracaoIntervalo = 0.08;

    public MatrizesContato(double[][][] tabela) {
        this.tabela = tabela;
        this.turmas = tabela[0].length;
        this.locais = tabela[0][0].length;
        this.dias = tabela.length;
    }
	
	public double[][] mediaTabelas(){
		
		double [][] media = new double[turmas][locais];
		for (int a = 0; a< turmas; a++){
			for (int b = 0; b < locais; b++){
				double soma = 0;
				for (int c = 0; c<dias; c++){
					soma = soma + tabela[c][a][b];
				}
				double mediaDias = soma/dias;
				media[a][b] = mediaDias;
			}
		}
		
		return media;
	}
	
	public double[] totalPorLocal(double[][] media){
		double [] somaPorLocal = new double[locais];
		for (int i = 0; i<locais;i++){
			double soma = 0;
			for (int j = 0; j<turmas;j++){
				soma = media[j][i] + soma;
			}
			somaPorLocal[i] = soma; 
		}
		return somaPorLocal;
	}
	
	public double[] naSala(double[][] media) { 
		double[] alunosNaSala = new double[turmas];

		for (int i = 0; i < turmas; i++) {
			double soma = 0;

			for (int j = 0; j < locais; j++) {
				soma = soma + media[i][j];
			}

			alunosNaSala[i] = tamanhoTurma[i] - soma;
		}

		return alunosNaSala;
	}
	
	public double[][] proporcaoContato(double[][] media, double[] somaPorLocal, double[] alunosNaSala){
		
		double[][] matrizContato = new double[turmas][turmas];
		
		for (int a = 0; a < turmas; a++) {
			for (int b = 0; b < turmas; b++) {
				double soma = 0;
				for (int l = 0; l < locais; l++) {
					double v = media[a][l] * media[b][l]/somaPorLocal[l];
					soma = soma +v;
				}
				if (a == b) {
					soma = soma + alunosNaSala[a];
				}
				matrizContato[a][b] = soma/tamanhoTurma[a];
			}
		}
		return matrizContato;
	}
	
	public double[][] proporcaoComTempo(double[][] proporcaoContato){
		double[][] matrizOk = new double[turmas][turmas];
		for (int a = 0; a<turmas; a++){
			double vsfd;
			for (int b = 0; b<turmas;b++){
				if (a==b){
					 vsfd = fracaoIntervalo*proporcaoContato[a][b]+(1-fracaoIntervalo); 
				} else{
					 vsfd = fracaoIntervalo*proporcaoContato[a][b];
				}
				matrizOk[a][b] = vsfd;
			}
		}
		return matrizOk;
	}
	
	public static void main(String args[]) {
		double[][][] tabela = {
			{//cantina, sala conv, patio, corredor baixo, bibliotca, corredor cima, quadra
				{2,0,3,1,2,0,3}, //info 1
				{3,0,3,0,2,6,1}, //info 2
				{1,1,0,0,2,1,2}, //info 3
				{7,0,0,0,0,0,6}, //info 4
				{2,4,2,2,0,0,7}, //agro 1
				{5,1,1,1,0,0,6}, // agro2
				{0,0,7,0,0,1,3}, //agro 3
				{0,0,0,0,1,0,0},//agro 4
				{8,0,6,4,0,0,7}, // adm1
				{3,0,0,2,0,2,3} //adm2
			},
			{
				{0,0,6,1,2,3,1}, //info 1
				{6,0,4,0,6,4,3}, //info 2
				{2,0,4,2,3,7,0}, //info 3
				{7,3,3,0,1,0,0}, //info 4
				{1,0,1,2,0,0,7}, //agro 1
				{3,0,8,3,0,0,10}, // agro2
				{0,0,8,6,0,0,2}, //agro 3
				{0,0,3,0,0,0,2},//agro 4
				{0,0,0,6,1,0,1}, // adm1
				{0,0,4,0,0,0,4} //adm2
			},
			{
				{1,0,5,1,0,5,0}, //info 1
				{4,1,6,0,2,4,1}, //info 2
				{7,0,3,0,3,4,3}, //info 3
				{0,4,3,0,0,1,7}, //info 4
				{2,0,3,4,0,0,3}, //agro 1
				{0,1,3,2,0,0,16}, // agro2
				{3,1,9,3,0,0,5}, //agro 3
				{0,0,4,0,0,0,1},//agro 4
				{4,4,5,6,0,0,5}, // adm1
				{5,0,7,0,0,0,4} //adm2
			}
		};
		MatrizesContato obj = new MatrizesContato(tabela);
		obj.tamanhoTurma = new double[]{33, 36,34,31,33,30,28,20,35,30}; 

		double[][] media = obj.mediaTabelas();
		double[] totalLocal = obj.totalPorLocal(media);
		double[] sala = obj.naSala(media);

		double[][] contato = obj.proporcaoContato(media, totalLocal, sala);

		System.out.println("Matriz de proporcao de contato:");

		for (int a = 0; a < contato.length; a++) {
			for (int b = 0; b < contato[a].length; b++) {
				System.out.printf("%.4f\t", contato[a][b]);
			}
			System.out.println();
		}
	}
}
