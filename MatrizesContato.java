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
}
