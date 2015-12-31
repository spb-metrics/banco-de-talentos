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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.BasicTransformerAdapter;

public class AliasLowerToMapResultTransformer extends BasicTransformerAdapter implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3226514300124978087L;

    public static final AliasLowerToMapResultTransformer INSTANCE = new AliasLowerToMapResultTransformer();

    /**
     * Instantiate AliasToEntityMapResultTransformer.
     * <p/>
     */
    private AliasLowerToMapResultTransformer()
    {}

    /**
     * {@inheritDoc}
     */
    public Object transformTuple(Object[] tuple, String[] aliases)
    {
        Map<String, Object> result = new HashMap<String, Object>(tuple.length);
        for (int i = 0; i < tuple.length; i++)
        {
            String alias = aliases[i];
            if (alias != null)
            {
                Object valorColuna = tuple[i];

                //    if (valorColuna instanceof BigInteger)
                //    {
                //        valorColuna = (Integer) ((BigInteger) valorColuna).intValue();
                //    }
                //    else if (valorColuna instanceof BigDecimal)
                //    {
                //        valorColuna = (Double) ((BigDecimal) valorColuna).doubleValue();
                //    }

                result.put(alias.toLowerCase(), valorColuna);
            }
        }
        return result;
    }

    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve()
    {
        return INSTANCE;
    }

    // all AliasToEntityMapResultTransformer are considered equal ~~~~~~~~~~~~~

    /**
     * All AliasToEntityMapResultTransformer are considered equal
     *
     * @param other The other instance to check for equality
     * @return True if (non-null) other is a instance of AliasToEntityMapResultTransformer.
     */
    public boolean equals(Object other)
    {
        // todo : we can remove this once the deprecated ctor can be made private...
        return other != null && AliasLowerToMapResultTransformer.class.isInstance(other);
    }

    /**
     * All AliasToEntityMapResultTransformer are considered equal
     *
     * @return We simply return the hashCode of the AliasToEntityMapResultTransformer class name string.
     */
    public int hashCode()
    {
        // todo : we can remove this once the deprecated ctor can be made private...
        return getClass().getName().hashCode();
    }

}
