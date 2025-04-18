public class PessoasCriadas {

    private int numeroPessoasCriadas;

    @Override
    public String toString(){
        return String.valueOf(numeroPessoasCriadas);
    }

    private PessoasCriadas(){
        numeroPessoasCriadas = 0;
    }

    public void setNumeroPessoasCriadas(Pessoa p){
        if (p != null){
            numeroPessoasCriadas++;
        }
    }

    public static PessoasCriadas getNewClass(){
        return new PessoasCriadas();
    }
}
