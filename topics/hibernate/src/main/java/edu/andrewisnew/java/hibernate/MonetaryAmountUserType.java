package edu.andrewisnew.java.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Properties;

public class MonetaryAmountUserType implements CompositeUserType, DynamicParameterizedType {

    protected Currency convertTo;

    @Override
    public void setParameterValues(Properties parameters) {
        ParameterType parameterType = (ParameterType) parameters.get(PARAMETER_TYPE);
        String[] columns = parameterType.getColumns();
        String table = parameterType.getTable();
        Annotation[] annotations = parameterType.getAnnotationsMethod();
        String convertToParameter = parameters.getProperty("convertTo");
        this.convertTo = Currency.getInstance(
                convertToParameter != null ? convertToParameter : "USD"
        );
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"amount", "currency"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StandardBasicTypes.INTEGER,
                StandardBasicTypes.CURRENCY
        };
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        Item.MonetaryAmount monetaryAmount = (Item.MonetaryAmount) component;
        if (property == 0)
            return monetaryAmount.getAmount();
        else
            return monetaryAmount.getCurrency();
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        Item.MonetaryAmount monetaryAmount = (Item.MonetaryAmount) component;
        if (property == 0) {
//            monetaryAmount.setAmount((int) value);
        } else {
//            monetaryAmount.setCurrency((Currency) value);
        }
    }

    @Override
    public Class returnedClass() {
        return Item.MonetaryAmount.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y || !(x == null || y == null) && x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        int amount = rs.getInt(names[0]);
        if (rs.wasNull())
            return null;
        Currency currency =
                Currency.getInstance(rs.getString(names[1]));
        return new Item.MonetaryAmount(amount, currency);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(
                    index,
                    StandardBasicTypes.INTEGER.sqlType());
            st.setNull(
                    index + 1,
                    StandardBasicTypes.CURRENCY.sqlType());
        } else {
            Item.MonetaryAmount amount = (Item.MonetaryAmount) value;
            Item.MonetaryAmount dbAmount = convert(amount, convertTo);
            st.setInt(index, dbAmount.getAmount());
            st.setString(index + 1, convertTo.getCurrencyCode());
        }

    }

    protected Item.MonetaryAmount convert(Item.MonetaryAmount amount, Currency toCurrency) {
        return new Item.MonetaryAmount(
                amount.getAmount() * 2,
                toCurrency
        );
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value, SharedSessionContractImplementor session) throws HibernateException {
        return value.toString();
    }

    @Override
    public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return Item.MonetaryAmount.fromString((String) cached);
    }

    @Override
    public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return original;
    }
}
