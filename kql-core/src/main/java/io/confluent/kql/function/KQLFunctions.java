/**
 * Copyright 2017 Confluent Inc.
 **/

package io.confluent.kql.function;

import io.confluent.kql.function.udaf.count.CountKUDAF;
import io.confluent.kql.function.udaf.sum.DoubleSumKUDAF;
import org.apache.kafka.connect.data.Schema;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import io.confluent.kql.function.udf.math.AbsKUDF;
import io.confluent.kql.function.udf.math.CeilKUDF;
import io.confluent.kql.function.udf.math.RandomKUDF;
import io.confluent.kql.function.udf.string.ConcatKUDF;
import io.confluent.kql.function.udf.math.FloorKUDF;
import io.confluent.kql.function.udf.string.IfNullKUDF;
import io.confluent.kql.function.udf.string.LCaseKUDF;
import io.confluent.kql.function.udf.string.LenKUDF;
import io.confluent.kql.function.udf.math.RoundKUDF;
import io.confluent.kql.function.udf.string.SubstringKUDF;
import io.confluent.kql.function.udf.string.TrimKUDF;
import io.confluent.kql.function.udf.string.UCaseKUDF;

public class KQLFunctions {

  public static Map<String, KQLFunction> kqlFunctionMap = new HashMap<>();
  public static Map<String, KQLFunction> kqlAggregateFunctionMap = new HashMap<>();

  static {

    /***************************************
     * String functions                     *
     ****************************************/

    KQLFunction lcase = new KQLFunction(Schema.Type.STRING, Arrays.asList(Schema.Type.STRING),
                                        "LCASE", LCaseKUDF.class);
    KQLFunction ucase = new KQLFunction(Schema.Type.STRING, Arrays.asList(Schema.Type.STRING),
                                        "UCASE", UCaseKUDF.class);
    KQLFunction substring = new KQLFunction(Schema.Type.STRING, Arrays.asList(Schema.Type
                                                                                  .STRING,
                                                                              Schema.Type
                                                                                  .INT32,
                                                                              Schema.Type
                                                                                  .INT32),
                                            "SUBSTRING", SubstringKUDF
                                                .class);
    KQLFunction concat = new KQLFunction(Schema.Type.STRING, Arrays.asList(Schema.Type.STRING,
                                                                           Schema.Type.STRING),
                                         "CONCAT", ConcatKUDF.class);

    KQLFunction trim = new KQLFunction(Schema.Type.STRING, Arrays.asList(Schema.Type.STRING),
                                       "TRIM", TrimKUDF.class);

    KQLFunction ifNull = new KQLFunction(Schema.Type.STRING, Arrays.asList(Schema.Type.STRING,
                                                                           Schema.Type.STRING),
                                         "IFNULL", IfNullKUDF.class);
    KQLFunction len = new KQLFunction(Schema.Type.INT32, Arrays.asList(Schema.Type.STRING),
                                      "LEN", LenKUDF.class);

    /***************************************
     * Math functions                      *
     ***************************************/

    KQLFunction abs = new KQLFunction(Schema.Type.FLOAT64, Arrays.asList(Schema.Type.FLOAT64),
                                      "ABS", AbsKUDF.class);
    KQLFunction ceil = new KQLFunction(Schema.Type.FLOAT64, Arrays.asList(Schema.Type.FLOAT64),
                                       "CEIL", CeilKUDF.class);
    KQLFunction floor = new KQLFunction(Schema.Type.FLOAT64, Arrays.asList(Schema.Type.FLOAT64),
                                        "FLOOR", FloorKUDF.class);
    KQLFunction
        round =
        new KQLFunction(Schema.Type.INT64, Arrays.asList(Schema.Type.FLOAT64), "ROUND",
                        RoundKUDF.class);
    KQLFunction random = new KQLFunction(Schema.Type.FLOAT64, new ArrayList<>(), "RANDOM",
                                         RandomKUDF.class);



    addFunction(lcase);
    addFunction(ucase);
    addFunction(substring);
    addFunction(concat);
    addFunction(len);
    addFunction(trim);
    addFunction(ifNull);

    addFunction(abs);
    addFunction(ceil);
    addFunction(floor);
    addFunction(round);
    addFunction(random);

    /***************************************
     * UDAFs                               *
     ***************************************/

    KQLFunction count = new KQLFunction(Schema.Type.INT32, Arrays.asList(Schema.Type.STRUCT),
                                        "COUNT", CountKUDAF.class);
    KQLFunction sum = new KQLFunction(Schema.Type.FLOAT64, Arrays.asList(Schema.Type.FLOAT64),
                                      "SUM", DoubleSumKUDAF.class);

    addAggregateFunction(count);
    addAggregateFunction(sum);

  }

  public static KQLFunction getFunction(String functionName) {
    return kqlFunctionMap.get(functionName.toUpperCase());
  }

  public static void addFunction(KQLFunction kqlFunction) {
    kqlFunctionMap.put(kqlFunction.getFunctionName().toUpperCase(), kqlFunction);
  }
  public static KQLFunction getAggregateFunction(String functionName) {
    return kqlAggregateFunctionMap.get(functionName.toUpperCase());
  }

  public static void addAggregateFunction(KQLFunction kqlFunction) {
    kqlAggregateFunctionMap.put(kqlFunction.getFunctionName().toUpperCase(), kqlFunction);
  }

}