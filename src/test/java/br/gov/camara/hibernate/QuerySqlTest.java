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

package br.gov.camara.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

import br.gov.camara.BaseTestesTestCase;
import br.gov.camara.biblioteca.exception.DataException;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.exception.DAOException;

public class QuerySqlTest extends BaseTestesTestCase
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(QuerySqlTest.class);

    /**
     * Obtem a data e hora atual do banco de dados
     */
    public void testObterHoraAtual()
    {
        log.info(MENSAGEM_INICIO_TESTE);
        assertNotNull(simulaObterTimeStamp(null));
    }

    private Date simulaObterTimeStamp(String nomeConexao)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String CHAVE_QUERY_TIMESTAMP_BD = "queryConsultaTimeStampBD";
        String CHAVE_NOME_COLUNA_TIMESTAMP = "timestamp";

        // Lê SQL para obtenção da data e hora
        String strConsulta = HibernateUtilCD.obterPropriedadeSubstituicao(nomeConexao, CHAVE_QUERY_TIMESTAMP_BD);

        if (log.isDebugEnabled())
        {
            log.debug("Consulta a ser executada para a obtenção da data e hora do banco de dados: " + strConsulta);
        }

        Date datDataHora = null;

        try
        {
            // Executa SQL
            List resultado = simularObterPorSql(nomeConexao, strConsulta);

            if (resultado == null || resultado.size() == 0)
            {
                log.debug("Nenhum resultado (data e hora) foi obtido do banco de dados.");
                throw new DAOException("Ocorreu um erro recuperando a hora do banco de dados");
            }

            // Pega primeiro registro
            Object resultadoDataHora = ((Map) resultado.get(0)).get(CHAVE_NOME_COLUNA_TIMESTAMP);
            if (resultadoDataHora instanceof Date)
            {
                datDataHora = (Date) resultadoDataHora;
            }
            else if (resultadoDataHora instanceof String)
            {
                try
                {
                    datDataHora = DataNova.formatarDate((String) resultadoDataHora);
                }
                catch (DataException de)
                {
                    CDException.dispararExcecao("Não foi possível formatar a data retornada pelo banco de dados." + resultadoDataHora);
                }

            }
            else
            {
                CDException.dispararExcecao("A query informada no hibernate-config não retorna um dado do tipo tratado (Date ou String).");
            }

        }
        catch (CDException e)
        {
            e.printStackTrace();
        }

        return datDataHora;

    }

    private List simularObterPorSql(String nomeConexao, String consulta) throws CDException
    {
        List lstResultado = null;
        try
        {
            lstResultado = HibernateUtilCD.carregarPorSQL(nomeConexao, consulta);
        }
        catch (HibernateException he)
        {
            CDException.dispararExcecao(he);
        }

        return lstResultado;

    }

}
