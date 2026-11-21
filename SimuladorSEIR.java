import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SimuladorSEIR {

	public static void main(String[] args) {
<<<<<<< HEAD
		double infectadosIniciais = 0;
		double alfa = 0.27;
		double sigma = 0.71;
		double beta = 0.21;
		int diasDeSimulacao = 0;
=======
		double infectadosIniciais = 5;
		double alfa = 0.27;
		double sigma = 0.71;
		double beta = 0.21;
		int diasDeSimulacao = 300;
>>>>>>> c8d4a2ca4403340140e2fc248b0556612158549c

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
		double[] tamanhos = {33, 36, 34, 31, 33, 30, 28, 20, 35, 30};
		String[] nomes = {"Info 1", "Info 2", "Info 3", "Info 4", "Agro 1","Agro 2", "Agro 3", "Agro 4", "Adm 1", "Adm 2"};
<<<<<<< HEAD
		
		Scanner sc = new Scanner(System.in);

        while (diasDeSimulacao < 1) {
            System.out.println("Quantos dias deseja simular?");
            diasDeSimulacao = sc.nextInt();
        }
        sc.nextLine();   // limpa o Enter que sobrou do nextint dos dias

        System.out.println("TURMA             TOTAL ALUNOS");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%-18s%d%n", nomes[i], (int) tamanhos[i]);
        }

        boolean boleana = false;
        int indiceTurma = 0;
        while (boleana == false) {
            System.out.println("Em qual das turmas se iniciará o contágio?");
            String turmaDeInicio = sc.nextLine();

            for (int i = 0; i < 10; i++) {
                if (turmaDeInicio.equalsIgnoreCase(nomes[i])) {
                    boleana = true;
                    indiceTurma = i;
                }
            }

            if (boleana == false) {
                System.out.println("Turma inválida");
            }
        }
        double totalNaTurma = tamanhos[indiceTurma];

        while (infectadosIniciais < 1 || infectadosIniciais > totalNaTurma) {
            System.out.println("Quantos infectados iniciais nessa turma? (1 a " + totalNaTurma + ")");
            infectadosIniciais = sc.nextInt();
            
            if (infectadosIniciais < 1 || infectadosIniciais > totalNaTurma) {
				System.out.println("Número inválido. Digite um valor entre 1 e " +totalNaTurma + ".");
			}
        }

		// matriz de contato final
		MatrizesContato obj = new MatrizesContato(tabela);
		obj.tamanhoTurma = tamanhos;
		double[][] media = obj.mediaTabelas();
		double[] totalLocal = obj.totalPorLocal(media);
		double[] sala = obj.naSala(media);
		double[][] contato = obj.proporcaoContato(media, totalLocal, sala);
		double[][] matriz = obj.proporcaoComTempo(contato);

		
		List<Populacao> turmas = new ArrayList<>();
		for (int a = 0; a < 10; a++) {
			double inf = 0;
			if (a == indiceTurma) {
=======

		// matriz de contato final
		MatrizesContato obj = new MatrizesContato(tabela);
		obj.tamanhoTurma = tamanhos;
		double[][] media = obj.mediaTabelas();
		double[] totalLocal = obj.totalPorLocal(media);
		double[] sala = obj.naSala(media);
		double[][] contato = obj.proporcaoContato(media, totalLocal, sala);
		double[][] matriz = obj.proporcaoComTempo(contato);

		// as 10 turmas (os infectados iniciais ficam só na Info 1(por enquanto))
		List<Populacao> turmas = new ArrayList<>();
		for (int a = 0; a < 10; a++) {
			double inf = 0;
			if (a == 0) {
>>>>>>> c8d4a2ca4403340140e2fc248b0556612158549c
				inf = infectadosIniciais;
			}
			turmas.add(new Populacao(nomes[a], tamanhos[a] - inf, 0, inf, 0, sigma, beta));
		}

		Simulador simulador = new Simulador(diasDeSimulacao, alfa, matriz);
<<<<<<< HEAD
		simulador.rodar(turmas);

=======
		List<RegistroDiario> historico = simulador.rodar(turmas);

		simulador.imprimirResultados(historico);
>>>>>>> c8d4a2ca4403340140e2fc248b0556612158549c
	}
}

class Populacao {
    String nome;
    double s;   // suscetíveis
    double e;   // expostos
    double i;   // infectados
    double r;   // recuperados
    double sigma;
    double beta;

    public Populacao(String nome, double s, double e, double i, double r, double sigma, double beta) {
        this.nome = nome;
        this.s = s;
        this.e = e;
        this.i = i;
        this.r = r;
        this.sigma = sigma;
        this.beta = beta;
    }

    public double populacaoTotal() {
        return s+e+i+r;
    }
    
     public void atualizarDia(double novasExposicoes) {
        double novosInfecciosos = sigma*e;
        double novasRecuperacoes = beta*i;

        s = s - novasExposicoes;
        e = e + novasExposicoes - novosInfecciosos;
        i = i + novosInfecciosos - novasRecuperacoes;
        r = r + novasRecuperacoes;

        // Proteção contra valores negativos 
        if (s < 0) {
            s = 0;
        }
        if (e < 0) {
            e = 0;
        }
        if (i < 0) {
            i = 0;
        }
    }
}

<<<<<<< HEAD
=======
class RegistroDiario {
    int dia;
    double s;
    double e;
    double i;
    double r;

    public RegistroDiario(int dia, double s, double e, double i, double r) {
        this.dia = dia;
        this.s = s;
        this.e = e;
        this.i = i;
        this.r = r;
    }
}

>>>>>>> c8d4a2ca4403340140e2fc248b0556612158549c

class Simulador {
	
    int totalDeDias;
    double alfa;
    double[][] matriz;

    public Simulador(int totalDeDias, double alfa, double[][] matriz) {
		
        this.totalDeDias = totalDeDias;
        this.alfa = alfa;
        this.matriz = matriz;
    }

    public void passarUmDia(List<Populacao> turmas) {
		
        double[] novasExposicoes = new double[10];

        // calcula as exposições de todas as turmas
        for (int a = 0; a < 10; a++) {
            double pressao = 0;
            for (int b = 0; b < 10; b++) {
                Populacao outra = turmas.get(b);
                pressao = pressao + matriz[a][b] * outra.i / outra.populacaoTotal();
            }
            novasExposicoes[a] = alfa * turmas.get(a).s * pressao;
        }

        // atualiza cada turma
        for (int a = 0; a < 10; a++) {
            turmas.get(a).atualizarDia(novasExposicoes[a]);
        }
    }


<<<<<<< HEAD
    void rodar(List<Populacao> turmas) {
        imprimirTurmas(0, turmas);

        for (int dia = 1; dia <= totalDeDias; dia++) {
            passarUmDia(turmas);
            if (dia % 7 == 0) {
                imprimirTurmas(dia, turmas);
            }
=======
    List<RegistroDiario> rodar(List<Populacao> turmas) {
        List<RegistroDiario> historico = new ArrayList<>();
        historico.add(totalDaEscola(0, turmas));

        for (int dia = 1; dia <= totalDeDias; dia++) {
            passarUmDia(turmas);
            historico.add(totalDaEscola(dia, turmas));
>>>>>>> c8d4a2ca4403340140e2fc248b0556612158549c
        }
    }
<<<<<<< HEAD
    
    // mostra S, E, I, R de cada turma em um dia
    public void imprimirTurmas(int dia, List<Populacao> turmas) {
        System.out.println("===== Dia " + dia + " =====");
        System.out.printf("%-8s %-7s %-7s %-7s %-7s%n", "Turma", "S", "E", "I", "R");
        for (int a = 0; a < turmas.size(); a++) {
            Populacao t = turmas.get(a);
            System.out.printf("%-8s %-7.1f %-7.1f %-7.1f %-7.1f%n", t.nome, t.s, t.e, t.i, t.r);
=======

    RegistroDiario totalDaEscola(int dia, List<Populacao> turmas) {
        double s = 0;
        double e = 0;
        double i = 0;
        double r = 0;

        for (int a = 0; a < turmas.size(); a++) {
            s = s + turmas.get(a).s;
            e = e + turmas.get(a).e;
            i = i + turmas.get(a).i;
            r = r + turmas.get(a).r;
        }

        return new RegistroDiario(dia, s, e, i, r);
    }

        public void imprimirResultados(List<RegistroDiario> historico) {
        System.out.printf("%-5s %-9s %-9s %-9s %-9s%n", "Dia", "S", "E", "I", "R");
        for (int k = 0; k < historico.size(); k++) {
            RegistroDiario registro = historico.get(k);
            System.out.printf("%-5d %-9.1f %-9.1f %-9.1f %-9.1f%n",
                    registro.dia, registro.s, registro.e, registro.i, registro.r);
>>>>>>> c8d4a2ca4403340140e2fc248b0556612158549c
        }
        System.out.println();
    }
}
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
			double valor;
			for (int b = 0; b<turmas;b++){
				if (a==b){
					 valor = fracaoIntervalo*proporcaoContato[a][b]+(1-fracaoIntervalo); 
				} else{
					 valor = fracaoIntervalo*proporcaoContato[a][b];
				}
				matrizOk[a][b] = valor;
			}
		}
		return matrizOk;
	}
}
