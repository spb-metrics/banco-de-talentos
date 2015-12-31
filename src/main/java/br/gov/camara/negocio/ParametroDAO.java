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

package br.gov.camara.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.type.DateType;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;
import br.gov.camara.negocio.exception.DAOException;

public class ParametroDAO extends HashMap<Object, Object>
{
    /**
    * Logger for this class
    */
    private static Log log = LogFactory.getLog(ParametroDAO.class);

    /**
     * 
     */
    private static final long serialVersionUID = -6305931874878925483L;

    // -1 -> Tipo de parâmetro indefinido
    // 0 -> Não definido, ou seja, ainda não utilizado
    // 1 -> Nomeado
    // 2 -> Posicional
    private int tipoParametro = 0;

    public ParametroDAO adicionar(String id, Object valor) throws DAOException
    {
        if (tipoParametro == 0)
        {
            tipoParametro = 1;
        }
        else if (tipoParametro != 1)
        {
            DAOException.dispararExcecao("Não se pode misturar os tipos de parâmetros.");
        }

        if (log.isDebugEnabled())
        {
            log.debug("Identificador: " + id + " - Valor: " + valor);
        }

        super.put(id, valor);
        return this;
    }

    public ParametroDAO adicionar(Integer id, Object valor) throws DAOException
    {
        if (tipoParametro == 0)
        {
            tipoParametro = 2;
        }
        else if (tipoParametro != 2)
        {
            DAOException.dispararExcecao("Não se pode misturar os tipos de parâmetros.");
        }

        if (log.isDebugEnabled())
        {
            log.debug("Identificador: " + id + " - Valor: " + valor);
        }

        super.put(id, valor);
        return this;
    }

    public Object put(Object id, Object valor)
    {
        Object retorno = null;

        if (id instanceof String)
        {
            try
            {
                retorno = adicionar((String) id, valor);
            }
            catch (DAOException e)
            {
                throw new RuntimeException(e.getMensagem());
            }
        }
        else if (id instanceof Integer)
        {
            try
            {
                retorno = adicionar((Integer) id, valor);
            }
            catch (DAOException e)
            {
                throw new RuntimeException(e.getMensagem());
            }
        }
        else
        {
            if (tipoParametro == 0)
            {
                tipoParametro = -1;
            }
            else if (tipoParametro != -1)
            {
                throw new RuntimeException("Não se pode misturar os tipos de parâmetros dentro de um ParametroDAO.");
            }

            if (log.isDebugEnabled())
            {
                log.debug("Identificador: " + id + " - Valor: " + valor);
            }
            retorno = super.put(id, valor);
        }

        return retorno;
    }

    public Query setarParametroQuery(Query query)
    {
        return setarParametroQuery(query, this);
    }

    public static Query setarParametroQuery(Query query, ParametroDAO parametros)
    {
        Set<Entry<Object, Object>> entrySet = (parametros == null ? null : parametros.entrySet());

        if (entrySet == null || entrySet.isEmpty())
        {
            return query;
        }

        //obtem os parametros caso algum tenha sido passado
        Iterator<Entry<Object, Object>> it = entrySet.iterator();
        Query queryComParametros = null;
        while (it.hasNext())
        {
            Entry<Object, Object> entry = it.next();
            queryComParametros = setarParametroQuery(query, entry.getKey(), entry.getValue());
        }
        return queryComParametros;
    }

    private static Query setarParametroQuery(Query query, Object chave, Object valor)
    {
        try
        {
            if (chave instanceof Integer || chave instanceof Long)
            {
                // Posicional
                int parameterIndex = Integer.parseInt(chave.toString());
                query.setParameter(parameterIndex - 1, valor);
            }
            else if (chave instanceof String)
            {
                // Por nome
                String parameterName = chave.toString();
                if (valor instanceof Collection)
                {
                    query.setParameterList(parameterName, (Collection) valor);
                }
                else if (valor instanceof Object[])
                {
                    query.setParameterList(parameterName, (Object[]) valor);
                }
                else if (valor instanceof Date)
                {
                    Date valorData = (Date) valor;
                    if (valorData.getHours() != 0 || valorData.getMinutes() != 0 || valorData.getSeconds() != 0)
                    {
                        // Tratar utilizando o padrão do Hibernate
                        query.setParameter(parameterName, valor);
                    }
                    else
                    {
                        // Forçar a indicação de data
                        query.setParameter(parameterName, valor, new DateType());
                    }
                }
                else
                {
                    query.setParameter(parameterName, valor);
                }
            }
            else
            {
                // Tipo de parâmetro não tratado
                throw new HibernateExceptionCD("O tipo de chave informada como parâmetro ainda não está sendo tratado");
            }
        }
        catch (Throwable throwable)
        {
            HibernateExceptionCD.dispararExcecao(throwable);
        }

        return query;
    }

}
