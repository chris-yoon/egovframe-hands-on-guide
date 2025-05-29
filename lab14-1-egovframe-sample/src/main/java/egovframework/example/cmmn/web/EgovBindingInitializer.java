package egovframework.example.cmmn.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

public class EgovBindingInitializer implements WebBindingInitializer {

  @Override
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
  }

  @Override
  public void initBinder(WebDataBinder binder, org.springframework.web.context.request.WebRequest request) {
    initBinder(binder);
  }
}
