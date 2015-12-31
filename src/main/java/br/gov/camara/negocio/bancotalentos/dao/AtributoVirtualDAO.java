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

package br.gov.camara.negocio.bancotalentos.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.ParametroDAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcaoVirtual;
import br.gov.camara.negocio.exception.DAOException;

/**
 * Classe DAO para a tabela AtributoTalentoOpcao
 * 
 */
public class AtributoVirtualDAO extends DAO
{

    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(AtributoTalentoDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public AtributoVirtualDAO()
    {
        super("Atributo Virtual");
    }

    public AtributoVirtualDAO(String nomeConexao)
    {
        super("Atributo Virtual", nomeConexao);
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorSQLParametrizado(String sql, Map parametrosConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List retorno = new ArrayList();

        try
        {
            List resultadoConsulta = obterPorSQLComParametros(sql, (ParametroDAO) parametrosConsulta);
            if (resultadoConsulta != null)
            {
                Iterator itrResultado = resultadoConsulta.iterator();
                while (itrResultado.hasNext())
                {
                    Map row = (Map) itrResultado.next();
                    Iterator colunas = row.keySet().iterator();

                    String strId = (String) row.get((String) colunas.next());

                    AtributoTalentoOpcaoVirtual aux = new AtributoTalentoOpcaoVirtual();
                    aux.setDescricao(strId);

                    // O identificador � o que ser� salvo no bd e armazena todas as colunas externas que devem ser apresentadas no curriculo
                    StringBuffer identificador = new StringBuffer(strId);
                    while (colunas.hasNext())
                    {
                        String nomeColuna = (String) colunas.next();
                        identificador.append("*|*" + nomeColuna + " : " + row.get(nomeColuna));
                    }
                    aux.setIdentificador(identificador.toString());

                    retorno.add(aux);
                }
            }
        }
        catch (Exception e)
        {
            throw new DAOException("N�o foi poss�vel obter valores do atributo virtual! Erro -> " + e);
        }

        return retorno;

    }

    protected String excluirImpl(Object objPOJO) throws DAOException
    {
        throw new DAOException("Not supported yet.");
    }

    public List obterTodos() throws DAOException
    {
        throw new DAOException("Not supported yet.");
    }

}
