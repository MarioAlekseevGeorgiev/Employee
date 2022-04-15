package dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public final class DaoUtils {

    private static SimpleDateFormat standardFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static void debugQuery(Log logger, String sql, MapSqlParameterSource mapSqlParameterSource) {
        if (null != logger && logger.isDebugEnabled()) {
            logMySQL(sql, mapSqlParameterSource, logger);
        }
    }

    public static void logMySQL(String sql, MapSqlParameterSource paramSource,Log logger)
    {
        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
        String sqlToUse = NamedParameterUtils.substituteNamedParameters(sql, paramSource);
        Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
        List<SqlParameter> paramsToShow = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        Map<String, String> paramValues = new HashMap<>();
        for (SqlParameter parameter : paramsToShow) {
            paramValues.put(parameter.getName(), String.valueOf(paramSource.getValue(parameter.getName())));
        }
        logger.debug(paramValues);
        logger.debug(substituteSQLBindings(sqlToUse, params));
    }

    public static String substituteSQLBindings(String sql, Object... args) {
        String substitutedSQL = null;

        try {
            substitutedSQL = sql.replaceAll("\\s+", " ");

            for(int i = 0; i < args.length; ++i) {
                if (args[i] == null) {
                    substitutedSQL = substitutedSQL.replaceFirst("\\?", "NULL");
                } else {
                    String s;
                    if (args[i] instanceof String) {
                        s = Matcher.quoteReplacement((String)args[i]);
                        substitutedSQL = substitutedSQL.replaceFirst("\\?", "'" + s + "'");
                    } else if (args[i] instanceof Integer) {
                        substitutedSQL = substitutedSQL.replaceFirst("\\?", args[i].toString());
                    } else if (args[i] instanceof Double) {
                        substitutedSQL = substitutedSQL.replaceFirst("\\?", args[i].toString());
                    } else if (args[i] instanceof Float) {
                        substitutedSQL = substitutedSQL.replaceFirst("\\?", args[i].toString());
                    } else if (args[i] instanceof Long) {
                        substitutedSQL = substitutedSQL.replaceFirst("\\?", args[i].toString());
                    } else if (args[i] instanceof Date) {
                        substitutedSQL = substitutedSQL.replaceFirst("\\?", "TO_DATE('" + standardFormat.format((Date)args[i]) + "', 'dd.mm.yyyy hh24:mi:ss')");
                    } else if (args[i].getClass().isArray()) {
                        int len = Array.getLength(args[i]);

                        for(int j = 0; j < len; ++j) {
                            substitutedSQL = substituteSQLBindings(substitutedSQL, Array.get(args[i], j));
                        }
                    } else {
                        s = args[i].toString();
                        if (s != null) {
                            s = Matcher.quoteReplacement(s);
                        }

                        substitutedSQL = substitutedSQL.replaceFirst("\\?", "'" + s + "'");
                    }
                }
            }
        } catch (Exception var6) {
          //  logger.error("exception in substituteSQLBindings(): " + var6.getMessage(), var6);
        }

        return substitutedSQL;
    }

    /**
     * Calculating indexes so the fetched result might be with one more then the the size of the page
     *
     * @param pageRequest pageable page request
     * @return a object holding the calculated indexes
     */
    public static PagingIndex pagingIdxForSlice(int pageSize, int pageNumbert) {

        final int base = pageSize * pageNumbert;

        PagingIndex pi = new PagingIndex();
        pi.setStartIdx(base + 1);
        pi.setEndIdx  (base + pageSize );
        pi.setPageSize(pageSize);

        return pi;
    }

    public static class PagingIndex{
        int startIdx;
        int endIdx;
        int pageSize;


        public int getStartIdx()
        {
            return startIdx;
        }

        public void setStartIdx(int startIdx)
        {
            this.startIdx = startIdx;
        }

        public int getEndIdx()
        {
            return endIdx;
        }

        public void setEndIdx(int endIdx)
        {
            this.endIdx = endIdx;
        }

        public int getPageSize()
        {
            return pageSize;
        }

        public void setPageSize(int pageSize)
        {
            this.pageSize = pageSize;
        }
    }

    public static <T> Slice<T> calcSlice(List<T> theList, Pageable pageRequest) {
        boolean hasNext = isHasNext(theList, pageRequest);

        return new SliceImpl<>(theList, pageRequest, hasNext);
    }

    private static <T> boolean isHasNext(List<T> theList, Pageable pageRequest) {
        Assert.notNull(theList, "theList must not be null!");
        Assert.notNull(pageRequest, "pageRequest must not be null!");

        boolean hasNext = false;
        final int size = theList.size();
        final int pageSize = pageRequest.getPageSize();

        if ( pageSize < size)
        {
            //todo: if the difference is more then one
            theList.remove(size - 1);
            hasNext = true;
        }
        return hasNext;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
