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

package sigesp.comum.util.hibernate.usertype;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Classe respons�vel por converter um campo byte[] em campo BLOB, essa classe � colocada nos mapeamentos hibernates
 * referente ao campo BLOB Ex: <property name="anexo" column="arq_anexo"
 * type="br.gov.camara.negocio.solicitacao.pojo.BinaryBlobType"/>
 * 
 * @author P_999326
 */
public class BinaryBlobType implements UserType
{

    private static final int BUFFER_SIZE = 1024;

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public int[] sqlTypes()
    {
        return new int[] { Types.BLOB };
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Class returnedClass()
    {
        return byte[].class;
    }

    public boolean equals(Object x, Object y)
    {
        return (x == y) || (x != null && y != null && Arrays.equals((byte[]) x, (byte[]) y));
    }

    public int hashCode(Object obj) throws HibernateException
    {
        return Hibernate.BLOB.getHashCode(obj, null);
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws SQLException
    {
        Blob blob = (Blob) Hibernate.BLOB.nullSafeGet(rs, names, null, owner);

        byte[] retorno = null;
        if (blob != null)
        {
            retorno = blob.getBytes(1, (int) blob.length());
        }

        return retorno;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws SQLException
    {
        if (value == null)
        {
            // Tratamento quando o valor � null
            Hibernate.BLOB.nullSafeSet(st, null, index, null);
        }
//        else if (st instanceof oracle.jdbc.OraclePreparedStatement)
//        {
//            // Tratamento "� la Oracle"
//            BLOB blobTemp = null;
//            try
//            {
//                blobTemp = BLOB.createTemporary(st.getConnection().getMetaData().getConnection(), false, BLOB.DURATION_SESSION);
//                blobTemp.open(BLOB.MODE_READWRITE);
//
//                OutputStream out = blobTemp.getBinaryOutputStream();
//                try
//                {
//                    out.write((byte[]) value);
//                    out.flush();
//                }
//                catch (IOException e)
//                {
//                    throw new SQLException("failed write to blob " + e.getMessage());
//                }
//                finally
//                {
//                    out.close();
//                }
//
//                blobTemp.close();
//
//                ((oracle.jdbc.OraclePreparedStatement) (st)).setBLOB(index, blobTemp);
//
//            }
//            catch (Exception e)
//            {
//                if (blobTemp != null && blobTemp.isOpen())
//                {
//                    try
//                    {
//                        blobTemp.close();
//                    }
//                    catch (Exception e2)
//                    {}
//                }
//                throw new SQLException("failed write to blob " + e.getMessage());
//            }
//        }
        else
        {
            // Tratamento padr�o
            Blob blob = Hibernate.createBlob((byte[]) value);
            Hibernate.BLOB.nullSafeSet(st, blob, index, null);
        }
    }

    public Object deepCopy(Object value) throws HibernateException
    {
        return Hibernate.BLOB.deepCopy(value, null, null);
    }

    public boolean isMutable()
    {
        return true; // http://www.hibernate.org/73.html => changed isMutable() to return true, since an array is a mutable object.
    }

    public Object assemble(Serializable serializable, Object obj) throws HibernateException
    {
        return Hibernate.BLOB.assemble(serializable, null, null);
    }

    public Serializable disassemble(Object obj) throws HibernateException
    {
        return Hibernate.BLOB.disassemble(obj, null, null);
    }

    public Object replace(Object obj, Object obj1, Object obj2) throws HibernateException
    {
        return Hibernate.BLOB.replace(obj, obj1, null, obj2, null);
    }

    protected byte[] copyData(InputStream input)
    {
        ByteArrayOutputStream output = null;

        try
        {
            output = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];

            int n = 0;
            while (-1 != (n = input.read(buffer)))
            {
                output.write(buffer, 0, n);
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Cannot copy data from InputStream into byte[]", ex);
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (IOException ex2)
            {}
        }

        return output.toByteArray();
    }
}
