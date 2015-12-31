/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package br.gov.camara.negocio.bancotalentos.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.biblioteca.util.ExpressaoRegular;
import br.gov.camara.biblioteca.util.Numero;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoOpcaoDAO;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoValoradoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaAtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.PessoaDAO;
import br.gov.camara.negocio.bancotalentos.dao.TalentoDAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;
import br.gov.camara.negocio.bancotalentos.pojo.TalentoVisualizacao;
import br.gov.camara.negocio.bancotalentos.util.GerenciadorAtributoVirtual;
import br.gov.camara.negocio.bancotalentos.util.IndexaTalento;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.exception.NegocioException;

/**
 * Facade para atributo de talento
 */
public class TalentoFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(TalentoFacade.class);

    /**
     * Obt�m o total de registros por pessoa
     * 
     * @param objPessoa Pessoa desejada
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPorPessoa(Pessoa objPessoa) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        TalentoDAO objTalentoDAO = new TalentoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objTalentoDAO.obterTotalRegistrosPorPessoa(objPessoa);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return intTotalRegistros;
    }

    /**
     * Obt�m o total de registros por pessoa e categoria de talento
     * 
     * @param objPessoa Pessoa desejada
     * @param objCategoriatalento Categoria de talento desejada
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPorPessoaCategoriaTalento(Pessoa objPessoa, CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        TalentoDAO objTalentoDAO = new TalentoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objTalentoDAO.obterTotalRegistrosPorPessoaCategoriaTalento(objPessoa, objCategoriaTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return intTotalRegistros;
    }

    /**
     * Obt�m os registros da pessoa desejada de determinada p�gina 
     *
     * @param objPessoa Pessoa desejada
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorPessoaPorPagina(Pessoa objPessoa, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        TalentoDAO objTalentoDAO = new TalentoDAO();
        List lstRetorno = new ArrayList();
        try
        {
            List lstTalentos = objTalentoDAO.obterPorPessoaPorPagina(objPessoa, intNumeroPagina, intMaximoPagina);
            Iterator itrTalentos = lstTalentos.iterator();
            while (itrTalentos.hasNext())
            {
                Talento objTalento = (Talento) itrTalentos.next();
                TalentoVisualizacao objTalentoVisualizacao = new TalentoVisualizacao();
                Copia.criar(objTalento, objTalentoVisualizacao);
                objTalentoVisualizacao.setDescricao(obterDescricao(objTalento));
                lstRetorno.add(objTalentoVisualizacao);
            }

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m os registros da pessoa desejada, de determinada categoria de talento e de determinada p�gina 
     *
     * @param objPessoa Pessoa desejada
     * @param objCategoriaTalento Categoria de talento desejada
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorPessoaCategoriaTalentoPorPagina(Pessoa objPessoa, CategoriaTalento objCategoriaTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        TalentoDAO objTalentoDAO = new TalentoDAO();
        List lstRetorno = new ArrayList();
        try
        {
            List lstTalentos = objTalentoDAO.obterPorPessoaCategoriaTalentoPorPagina(objPessoa, objCategoriaTalento, intNumeroPagina, intMaximoPagina);
            Iterator itrTalentos = lstTalentos.iterator();
            while (itrTalentos.hasNext())
            {
                Talento objTalento = (Talento) itrTalentos.next();
                TalentoVisualizacao objTalentoVisualizacao = new TalentoVisualizacao();
                Copia.criar(objTalento, objTalentoVisualizacao);
                objTalentoVisualizacao.setDescricao(obterDescricao(objTalento));
                lstRetorno.add(objTalentoVisualizacao);
            }

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m a descri��o de determinado talento
     *
     * @param objTalento Talento a ser descrito
     * 
     * @return String Contendo a descri��o
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public String obterDescricao(Talento objTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        String strDescricao = "";
        try
        {
            if ("S".equals(objTalento.getCategoriaTalento().getIndicativoUnicidade()))
            {
                strDescricao = objTalento.getCategoriaTalento().getNome();
            }
            else
            {
                List lstRetorno = objAtributoTalentoValoradoDAO.obterPeloTalento(objTalento);
                Iterator itrRetorno = lstRetorno.iterator();
                while (itrRetorno.hasNext())
                {
                    AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrRetorno.next();
                    if ("S".equals(objAtributoTalentoValorado.getCategoriaAtributoTalento().getFormacaoDescricao()))
                    {
                        strDescricao += objAtributoTalentoValorado.getValoracao() + "/";
                    }
                }
                if ("".equals(strDescricao))
                {
                    strDescricao = objTalento.getCategoriaTalento().getNome();
                }
                else
                {
                    strDescricao = strDescricao.substring(0, strDescricao.length() - 1);
                }
            }

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return strDescricao;
    }

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Talento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public Talento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        TalentoDAO objTalentoDAO = new TalentoDAO();
        Talento objTalento = null;
        try
        {
            objTalento = (Talento) objTalentoDAO.obterPelaChave(strChave);
            objTalentoDAO.inicializarCategoriaTalento(objTalento);

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objTalento;
    }

    /**
     * Obt�m um registro de pessoa a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Pessoa POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public Pessoa obterPessoaPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        PessoaDAO objPessoaDAO = new PessoaDAO();
        Pessoa objPessoa = null;
        try
        {
            objPessoa = (Pessoa) objPessoaDAO.obterPelaChave(strChave);
            if (objPessoa != null)
            {
                objPessoaDAO.inicializarGrupo(objPessoa);
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objPessoa;
    }

    /**
     * Obt�m um registro a partir da chave, incluindo tamb�m a descri��o
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return TalentoVisualizacao POJO (extendido) representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public TalentoVisualizacao obterPelaChaveComDescricao(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        TalentoDAO objTalentoDAO = new TalentoDAO();
        Talento objTalento = null;
        TalentoVisualizacao objTalentoVisualizacao = null;
        try
        {
            objTalento = (Talento) objTalentoDAO.obterPelaChave(strChave);
            objTalentoVisualizacao = new TalentoVisualizacao();
            Copia.criar(objTalento, objTalentoVisualizacao);
            objTalentoVisualizacao.setDescricao(obterDescricao(objTalento));
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objTalentoVisualizacao;
    }

    /**
     * Obt�m as categorias de talento dispon�veis para o usu�rio desejado
     * 
     * @param objPessoa Pessoa a buscar os talentos
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCategoriasTalento(Pessoa objPessoa) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objCategoriaTalentoDAO.obterTodosPorGrupo(objPessoa.getGrupo());
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obt�m uma categoria de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CategoriaTalento obterCategoriaTalentoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        CategoriaTalento objCategoriaTalento = null;
        try
        {
            objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objCategoriaTalento;
    }

    /**
     * Obt�m uma categoria/atributo de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaAtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CategoriaAtributoTalento obterCategoriaAtributoTalentoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        CategoriaAtributoTalento objCategoriaAtributoTalento = null;
        try
        {
            objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objCategoriaAtributoTalento;
    }

    /**
     * Obt�m uma op��o de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalentoOpcao POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public AtributoTalentoOpcao obterAtributoTalentoOpcaoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        AtributoTalentoOpcao objAtributoTalentoOpcao = null;
        try
        {
            objAtributoTalentoOpcao = (AtributoTalentoOpcao) objAtributoTalentoOpcaoDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objAtributoTalentoOpcao;
    }

    /**
     * Obt�m os relacionamentos categoria/atributo para a categoria informada
     *
     * @param objCategoriaTalento Categoria de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCategoriaAtributosTalento(CategoriaTalento objCategoriaTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstCategoriaAtributosTalento = null;
        try
        {
            lstCategoriaAtributosTalento = objCategoriaAtributoTalentoDAO.obterPorCategoriaTalento(objCategoriaTalento);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstCategoriaAtributosTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstCategoriaAtributosTalento;
    }

    /**
     * Obt�m os relacionamentos categoria/atributo que s�o filhos da categoria/atributo informado
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCategoriaAtributosTalentoFilhos(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        List lstCategoriaAtributosTalentoFilhos = null;
        try
        {
            lstCategoriaAtributosTalentoFilhos = objCategoriaAtributoTalentoDAO.obterTodosOsFilhos(objCategoriaAtributoTalento);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(lstCategoriaAtributosTalentoFilhos);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstCategoriaAtributosTalentoFilhos;
    }

    /**
     * Obt�m as valora��es do talento especificado
     *
     * @param objTalento Talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterValoracoesPorTalento(Talento objTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        List lstAtributosTalentoValorados = null;
        try
        {
            lstAtributosTalentoValorados = objAtributoTalentoValoradoDAO.obterPeloTalento(objTalento);
            objAtributoTalentoValoradoDAO.inicializarCategoriaAtributoTalento(lstAtributosTalentoValorados);
            objAtributoTalentoValoradoDAO.inicializarAtributoTalentoOpcao(lstAtributosTalentoValorados);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributosTalentoValorados;
    }

    /**
     * Obt�m as op��es filhas de determinada op��o pai
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterOpcoesPeloPaiEAtributoTalento(AtributoTalentoOpcao objAtributoTalentoOpcaoPai, AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoes = null;
        try
        {
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoDAO.obterPeloPaiEAtributoTalento(objAtributoTalentoOpcaoPai, objAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoes;
    }

    /**
     * Obt�m as op��es filhas de determinada op��o pai
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterOpcoesPeloPai(AtributoTalentoOpcao objAtributoTalentoOpcaoPai) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoes = null;
        try
        {
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoDAO.obterPeloPai(objAtributoTalentoOpcaoPai);
        }
        catch (DAOException daoe)
        {
            throw new CDException(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoes;
    }

    /**
     * Obt�m as op��es de determinado atributo de talento (desde que n�o tenha filhos) , se existirem
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterOpcoesPorAtributoTalento(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoes = null;
        try
        {
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoDAO.obterPorAtributoTalento(objAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoes;
    }

    /**
     * Obt�m as op��es de um determinado atributo talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterOpcoesAtributoTalento(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoes = null;
        try
        {
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoDAO.obterPorAtributoTalento(objAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoes;
    }

    /**
     * Obt�m a hierarquia superior da op��o desejada (incluindo ela)
     *
     * @param objAtributoTalentoOpcao Op��o de atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterAtributoTalentoOpcaoHierarquiaSuperior(AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();
        List lstAtributoTalentoOpcoesHierarquiaSuperior = new ArrayList();
        try
        {
            objAtributoTalentoOpcao = (AtributoTalentoOpcao) objAtributoTalentoOpcaoDAO.obterPelaChave(String.valueOf(objAtributoTalentoOpcao.getIdentificador()));
            lstAtributoTalentoOpcoesHierarquiaSuperior = objAtributoTalentoOpcaoFacade.obterHierarquiaSuperior(objAtributoTalentoOpcao);
            lstAtributoTalentoOpcoesHierarquiaSuperior.add(objAtributoTalentoOpcao);

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoesHierarquiaSuperior;
    }

    /**
     * Obt�m a hierarquia inferior da categoria/atributo desejada
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de atributo de talento desejada
     * 
     * @return List Contendo os registros
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCategoriaAtributoTalentoHierarquiaInferior(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();
        List lstCategoriaAtributoTalentoHierarquiaInferior = new ArrayList();
        try
        {
            lstCategoriaAtributoTalentoHierarquiaInferior = objCategoriaAtributoTalentoFacade.obterHierarquiaInferior(objCategoriaAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstCategoriaAtributoTalentoHierarquiaInferior;
    }

    /**
     * Verifica se determinado atributo de talento t�m filhos na rela��o categoria/atributo
     *
     * @param objCategoriaAtributoTalento Categoria/atributo de talento desejado
     * 
     * @return boolean Com a verifica��o
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public boolean verificarExistenciaCategoriaAtributosFilhos(CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        boolean blnExistenciaFilhos = false;
        try
        {
            blnExistenciaFilhos = objCategoriaAtributoTalentoDAO.verificarExistenciaCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return blnExistenciaFilhos;
    }

    /**
     * Obt�m todos os registros 
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m todos os registros
        TalentoDAO objTalentoDAO = new TalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objTalentoDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * 
     * Obt�m categoria/atributo de acordo com a categoria e o atributo passados
     * 
     * @param objCategoriaTalento Categoria de talento a ser consultada
     * @param objAtributoTalento Atributo de talento a ser consultado
     * 
     * @return CategoriaAtributoTalento Contendo a categoria/atributo
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public CategoriaAtributoTalento obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(CategoriaTalento objCategoriaTalento, AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m todos os registros
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        CategoriaAtributoTalento objCategoriaAtributoTalento = null;
        try
        {
            objCategoriaAtributoTalento = objCategoriaAtributoTalentoDAO.obterPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objCategoriaAtributoTalento;

    }

    /**
     * Inclui um registro
     *
     * @param objTalento POJO representando o objeto a ser inclu�do
     * @param lstAtributosTalentoValoros List contendo as valora��es do talento a serem inclu�das
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(Talento objTalento, List lstAtributosTalentoValorados) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        TalentoDAO objTalentoDAO = new TalentoDAO();
        CategoriaTalentoDAO objCategoriaTalentoDAO = new CategoriaTalentoDAO();
        String strChave = null;
        try
        {
            // N�o � permitido que a pessoa tenha mais de uma categoria cadastrada se ela for perfil
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) objCategoriaTalentoDAO.obterPelaChave(String.valueOf(objTalento.getCategoriaTalento().getIdentificador()));
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                if (objTalentoDAO.verificarExistenciaPorPessoaCategoriaTalento(objTalento.getPessoa(), objTalento.getCategoriaTalento()))
                {
                    throw new NegocioException("N�o � poss�vel inserir mais de um talento se a categoria " + "em quest�o � perfil");
                }
            }

            // Verifica as valora��es obrigat�rios
            verificarValoracoesObrigatorias(objTalento.getCategoriaTalento(), lstAtributosTalentoValorados);

            // Inicia transa��o
            DAO.iniciarTransacao();

            // Preenche dados de talento
            objTalento.setDataLancamento(DataNova.formatarCalendar(DataNova.obterDataAtual()));

            // Inclui talento
            strChave = objTalentoDAO.incluir(objTalento);
            objTalento.setIdentificador(Integer.valueOf(strChave));

            // Inclui valora��es
            incluirValoracoes(objTalento, lstAtributosTalentoValorados);

            // Realiza transa��o
            DAO.realizarTransacao();

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        // Retorna chave
        return strChave;
    }

    private void verificarValoracoesObrigatorias(CategoriaTalento objCategoriaTalento, List lstAtributosTalentoValorados) throws CDException
    {
        try
        {
            // Declara��es
            CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();

            // Obt�m categoria/atributos da categoria
            List lstCategoriaAtributosTalento = objCategoriaAtributoTalentoDAO.obterPorCategoriaTalento(objCategoriaTalento);

            // Verifica os atributos de talento obrigat�rios 
            Iterator itrCategoriaAtributosTalento = lstCategoriaAtributosTalento.iterator();
            while (itrCategoriaAtributosTalento.hasNext())
            {
                // Obt�m categoria/atributo atual e marca a presen�a como falsa
                CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) itrCategoriaAtributosTalento.next();
                boolean blnPresente = false;
                boolean blnPreenchido = false;

                // Verifica o nome do atributo de talento
                String strNomeCampo;
                if (objCategoriaAtributoTalento.getApelido() == null || "".equals(objCategoriaAtributoTalento.getApelido()))
                {
                    strNomeCampo = objCategoriaAtributoTalento.getAtributoTalento().getNome();
                }
                else
                {
                    strNomeCampo = objCategoriaAtributoTalento.getApelido();
                }

                // Verifica se o atributo est� presente e foi preenchido
                Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
                while (itrAtributosTalentoValorados.hasNext())
                {
                    AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrAtributosTalentoValorados.next();
                    if (objCategoriaAtributoTalento.getIdentificador().equals(objAtributoTalentoValorado.getCategoriaAtributoTalento().getIdentificador()))
                    {
                        blnPresente = true;
                        if (objAtributoTalentoValorado.getValoracao() != null && !"".equals(objAtributoTalentoValorado.getValoracao()))
                        {
                            blnPreenchido = true;
                            break;
                        }
                    }
                }

                // Verifica se o atributo n�o est� presente e � obrigat�rio
                if (!blnPreenchido && "S".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()))
                {
                    // Se for obrigat�rio, dispara um erro
                    throw new CDException("O atributo " + strNomeCampo + " � obrigat�rio");
                }
                else if (!blnPresente)
                {
                    // Se n�o for obrigat�rio, cria valora��o vazia e insere na lista
                    AtributoTalentoValorado objAtributoTalentoValorado = new AtributoTalentoValorado();
                    objAtributoTalentoValorado.setCategoriaAtributoTalento(objCategoriaAtributoTalento);
                    objAtributoTalentoValorado.setValoracao("");
                    lstAtributosTalentoValorados.add(objAtributoTalentoValorado);
                }
            }

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    private void incluirValoracoes(Talento objTalento, List lstAtributosTalentoValorados) throws CDException
    {
        // Declara��es
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();

        try
        {

            // Inclui valora��es
            Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                // Obt�m valora��o
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrAtributosTalentoValorados.next();

                // INICIO - Lucene no Banco de Talentos
                if (objAtributoTalentoValorado.getValoracao() == null || "".equals(objAtributoTalentoValorado.getValoracao()))
                {
                    continue;
                }
                // FIM - Lucene no Banco de Talentos

                // Seta talento
                objAtributoTalentoValorado.setTalento(objTalento);

                // Obt�m categoria/atributo relacionado 
                CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalentoValorado.getCategoriaAtributoTalento().getIdentificador()));

                // INICIO - Lucene no Banco de Talentos
                objAtributoTalentoValorado.setCategoriaAtributoTalento(objCategoriaAtributoTalento);
                // Obtem o AtributoTalento correspondente a CategoriaAtributoTalento porque sera necessario usar no Lucene depois
                // objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(objAtributoTalentoValorado.getCategoriaAtributoTalento());
                // O codigo acima tambem nao funcionou, mas neste caso pode ser porque nem mesmo o id de AtributoTalento estava na classe CategoriaAtributoTalento
                // Para resolver alterei o mapeamento e tornei o atributo AtributoTalento n�o lazy na classe CategoriaAtributoTalento
                // FIM - Lucene no Banco de Talentos

                // Verifica o nome do atributo de talento
                String strNomeCampo;
                if (objCategoriaAtributoTalento.getApelido() == null || "".equals(objCategoriaAtributoTalento.getApelido()))
                {
                    strNomeCampo = objCategoriaAtributoTalento.getAtributoTalento().getNome();
                }
                else
                {
                    // TODO Analisar se deve prevalecer o c�digo do Christian que est� abaixo
                    // strNomeCampo = objAtributoTalentoValorado.getCategoriaAtributoTalento().getApelido();

                    strNomeCampo = objCategoriaAtributoTalento.getApelido();
                }

                //Trata atributo virtual
                if (GerenciadorAtributoVirtual.isAtributoVirtual(objCategoriaAtributoTalento.getAtributoTalento().getNome()))
                {
                    //se for um atributo virtual j� tem que ir direto para inclus�o
                }
                // Verifica se a categoria/atributo recebido tem op��es, e se tiver, cria o objeto AtributoTalentoOpcao
                // e o relaciona, se n�o tiver, valida o tipo de dado
                else if ("U".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade())
                        || "M".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                {
                    if (objAtributoTalentoValorado.getValoracao() != null && !"".equals(objAtributoTalentoValorado.getValoracao()))
                    {
                        AtributoTalentoOpcao objAtributoTalentoOpcao = new AtributoTalentoOpcao();
                        objAtributoTalentoOpcao.setIdentificador(Integer.valueOf(objAtributoTalentoValorado.getValoracao()));
                        objAtributoTalentoOpcao = (AtributoTalentoOpcao) objAtributoTalentoOpcaoDAO.obterPelaChave(String.valueOf(objAtributoTalentoOpcao.getIdentificador()));
                        objAtributoTalentoValorado.setAtributoTalentoOpcao(objAtributoTalentoOpcao);
                        objAtributoTalentoValorado.setValoracao(objAtributoTalentoOpcao.getDescricao());
                    }
                }
                else
                {
                    // Valida o tipo de dado
                    if ("T".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                    {
                        if (objCategoriaAtributoTalento.getAtributoTalento().getMascara() != null
                                && !"".equals(objCategoriaAtributoTalento.getAtributoTalento().getMascara())
                                && objAtributoTalentoValorado.getValoracao() != null
                                && !"".equals(objAtributoTalentoValorado.getValoracao()))
                        {
                            if (!ExpressaoRegular.validar(objCategoriaAtributoTalento.getAtributoTalento().getMascara(), objAtributoTalentoValorado.getValoracao()))
                            {
                                throw new CDException("O atributo " + strNomeCampo + " deve ser num�rico");
                            }
                        }
                    }
                    if ("N".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                    {
                        if (objAtributoTalentoValorado.getValoracao() != null
                                && !"".equals(objAtributoTalentoValorado.getValoracao())
                                && !Numero.validarNumero(objAtributoTalentoValorado.getValoracao()))
                        {
                            throw new CDException("O atributo " + strNomeCampo + " deve ser num�rico");
                        }
                    }
                    if ("D".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                    {
                        if (objAtributoTalentoValorado.getValoracao() != null
                                && !"".equals(objAtributoTalentoValorado.getValoracao())
                                && (objAtributoTalentoValorado.getValoracao().trim().length() != 10 || !DataNova.validarData(objAtributoTalentoValorado.getValoracao())))
                        {
                            throw new CDException("O atributo " + strNomeCampo + " deve ser uma data v�lida");
                        }
                    }
                }

                // Inclui valora��o
                objAtributoTalentoValoradoDAO.incluir(objAtributoTalentoValorado);

                // INICIO - Lucene no Banco de Talentos
                // Trata insercao no Lucene
                // Se der algum problema, a transacao no banco vai continuar e um log com o
                // erro no Lucene ser� gerado
                IndexaTalento idx = null;
                try
                {
                    // Tenta excluir campos do tipo "D"ata e "N"umero
                    // Nem sempre o tipo de dado esta preenchido
                    if (objAtributoTalentoValorado.getCategoriaAtributoTalento().getAtributoTalento().getTipoDado() == null
                            || "T".equals(objAtributoTalentoValorado.getCategoriaAtributoTalento().getAtributoTalento().getTipoDado()))
                    {
                        //Incluir no Lucene
                        idx = IndexaTalento.getInstance();
                        idx.addTalento(objTalento.getPessoa().getIdentificador().toString(), objAtributoTalentoValorado.getValoracao());
                    }
                }
                catch (IOException e)
                {
                    if (log.isErrorEnabled())
                    {
                        log.error(e);
                    }
                }
                finally
                {
                    if (idx != null)
                    {
                        try
                        {
                            idx.close();
                        }
                        catch (Exception e)
                        {}
                    }
                }
                // FIM - Lucene no Banco de Talentos

            }
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Altera um registro
     *
     * @param objTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(Talento objTalento, List lstAtributosTalentoValorados) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        TalentoDAO objTalentoDAO = new TalentoDAO();
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        try
        {
            // Verifica as valora��es obrigat�rios
            verificarValoracoesObrigatorias(objTalento.getCategoriaTalento(), lstAtributosTalentoValorados);

            // Inicia transa��o
            DAO.iniciarTransacao();

            // Preenche dados de talento
            objTalento.setDataLancamento(DataNova.formatarCalendar(DataNova.obterDataAtual()));

            // Exclui valora��es anteriores
            objAtributoTalentoValoradoDAO.excluirPorTalento(objTalento);

            // Reinclui valora��es
            incluirValoracoes(objTalento, lstAtributosTalentoValorados);

            // Altera
            objTalentoDAO.alterar(objTalento);

            // Realiza transa��o
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Exclui um registro
     *
     * @param objTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(Talento objTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        TalentoDAO objTalentoDAO = new TalentoDAO();
        try
        {
            // Inicia transa��o
            DAO.iniciarTransacao();

            // Exclui valora��es
            objAtributoTalentoValoradoDAO.excluirPorTalento(objTalento);

            // Exclui
            objTalentoDAO.excluir(objTalento);

            // Realiza transa��o
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }
}
