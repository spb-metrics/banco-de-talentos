package br.gov.camara.seguranca.exception;

import br.gov.camara.exception.CDException;

public class SegurancaException extends CDException
{
    private static final long serialVersionUID = -3940471412982630188L;

    public SegurancaException()
    {
        super("Erro inesperado na biblioteca de segurança.");
    }

    public SegurancaException(String mensagem)
    {
        super(mensagem);
    }

    public SegurancaException(String mensagem, String mensagemUsuario)
    {
        super(mensagem, mensagemUsuario);
    }

    public SegurancaException(Throwable causa)
    {
        super(causa);
    }

    public SegurancaException(String mensagem, Throwable causa)
    {
        super(mensagem, causa);
    }

    public SegurancaException(String mensagem, Throwable causa, String mensagemUsuario)
    {
        super(mensagem, causa, mensagemUsuario);
    }

    public SegurancaException(Throwable causa, String mensagemUsuario)
    {
        super(causa, mensagemUsuario);
    }

    public static void dispararExcecao(Throwable erro) throws SegurancaException
    {
        if (erro instanceof SegurancaException)
        {
            throw ((SegurancaException) erro);
        }

        throw new SegurancaException(getMensagem(erro), erro);
    }

    public static void dispararExcecao(String mensagem) throws SegurancaException
    {
        throw new SegurancaException(mensagem);
    }

}
