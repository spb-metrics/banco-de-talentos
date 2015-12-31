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
