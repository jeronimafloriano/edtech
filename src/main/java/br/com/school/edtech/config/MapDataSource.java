package br.com.school.edtech.config;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Iterator;
import java.util.Map;

public class MapDataSource implements JRDataSource {
  private Iterator<Map.Entry<String, Double>> iterator;
  private Map.Entry<String, Double> currentEntry;

  public MapDataSource(Map<String, Double> map) {
    this.iterator = map.entrySet().iterator();
  }

  @Override
  public boolean next() throws JRException {
    if (iterator.hasNext()) {
      currentEntry = iterator.next();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Object getFieldValue(JRField field) throws JRException {
    if ("nome_curso".equals(field.getName())) {
      return currentEntry.getKey();
    } else if ("nps".equals(field.getName())) {
      return currentEntry.getValue();
    }
    return null;
  }
}