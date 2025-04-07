public class Iphone implements NavegadorInternet, ReprodutorMusical, AparelhoTelefonico{

    @Override
    public void pausar(){
        System.out.println("Musica pausada");
    }

    @Override
    public void tocar(){
        System.out.println("Tocando música");
    }

    @Override
    public void selecionarMusica(String musica){
        System.out.printf("Selecionando música %s",musica);
    }

    @Override
    public void ligar(String numero){
        System.out.printf("Ligando para o número %s",numero);
    }

    @Override
    public void atender(){
        System.out.println("Atendendo.");
    }

    @Override
    public void iniciarCorreioVoz(){
        System.out.println("Iniciando Correio de Voz");
    }

    @Override
    public void exibirPagina(String url){
        System.out.printf("Exibindo página da url: %s",url);
    }

    @Override
    public void adicionarNovaAba(){
        System.out.println("Adicionando nova aba");
    }

    @Override
    public void atualizarPagina(){
        System.out.println("Atualizando página");
    }
}
