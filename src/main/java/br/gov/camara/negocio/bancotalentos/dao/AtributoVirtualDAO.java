/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
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

    // Variáveis de instância
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
     * Obtém todos os registros
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

                    // O identificador é o que será salvo no bd e armazena todas as colunas externas que devem ser apresentadas no curriculo
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
            throw new DAOException("Não foi possível obter valores do atributo virtual! Erro -> " + e);
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
