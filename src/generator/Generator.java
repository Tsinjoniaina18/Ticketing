package generator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import annotation.Form;
import annotation.Input;
import annotation.Label;
import annotation.Select;

import util.Utils;

public class Generator {

    public static String generateLabel(Field field) throws Exception{
        Label label = field.getAnnotation(Label.class);
        String value = label.valueLabel();

        Utils.validationGeneration("Label", "value", value);

        String forValue = label.forValueLabel();

        String s = " <label for=\""+forValue+"\">"+value+"</label>";

        return s;
    }

    public static String generateInput(Field field) throws Exception {
        Input input = field.getAnnotation(Input.class);
        String name = input.nameInput();

        Utils.validationGeneration("Input", "name", name);

        StringBuilder s = new StringBuilder("<input");

        Utils.generateAttributesFromAnnotation(input, s, 1, "Input");

        s.append(" />");

        return s.toString();
    }

    public static String generateSelect(Field field, HashMap<String, ArrayList> hashMap)throws Exception{
        Select select = field.getAnnotation(Select.class);

        Utils.validationGeneration("Select", "name", select.nameSelect());
        Utils.validationGeneration("Select", "reference", select.reference());
        Utils.validationGeneration("Select", "field value", select.referenceFieldValue());

        StringBuilder s = new StringBuilder("<select");

        Utils.generateAttributesFromAnnotation(select, s, 1, "Select");

        s.append(">");

        ArrayList obj = hashMap.get(select.reference());
        if (obj!=null) {
            generateOptions(select, obj, s);
        }else{
            throw new Exception("Invalid reference: "+select.reference());
        }

        s.append("</select>");
        return s.toString();
    }

    public static void generateOptions(Select select, ArrayList objects, StringBuilder s)throws Exception{
        String fieldValue = select.referenceFieldValue();
        String fieldName = select.referenceFieldName();

        fieldValue = Utils.capitalizeFirstLetter(fieldValue);
        fieldName = Utils.capitalizeFirstLetter(fieldName);
        
        Object obj;
        Method methodValue;
        Method methodName;
        Object value;
        Object name;
        for(int i=0; i<objects.size(); i++){
            obj = objects.get(i);

            methodValue = obj.getClass().getDeclaredMethod("get"+fieldValue, null);
            methodName = obj.getClass().getDeclaredMethod("get"+fieldName, null);

            value = methodValue.invoke(obj, null);
            name =  methodName.invoke(obj, null);

            s.append("<option").append(" ").append("value=\"").append(value).append("\"").append(">").append(name).append("</option>");
        }
    }

    public static String generateFormHeader(Object obj)throws Exception{
        if (obj.getClass().isAnnotationPresent(Form.class)) {
            Form form = obj.getClass().getAnnotation(Form.class);
            String action = form.actionForm();

            Utils.validationGeneration("Form", "action", action);

            StringBuilder s = new StringBuilder("<form");

            Utils.generateAttributesFromAnnotation(form, s, 1, "Form");

            s.append(" >");

            return s.toString();
        } else {
            throw new Exception("Missing annotation Form from the class " + obj.getClass().getName());
        }
    }

    public static String generateFormContent(Object obj, HashMap<String, ArrayList> hashMap)throws Exception{
        StringBuilder s = new StringBuilder();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            s.append("<div class=\"form-group\">");

            if (fields[i].isAnnotationPresent(Label.class)) {
                s.append(generateLabel(fields[i]));
            }
            if (fields[i].isAnnotationPresent(Input.class)) {
                s.append(generateInput(fields[i]));
            } else if(fields[i].isAnnotationPresent(Select.class)){
                s.append(generateSelect(fields[i], hashMap));
            }

            s.append("</div>");

        }

        return s.toString();
    }

    public static String generateFormFooter(){
        StringBuilder s = new StringBuilder();

        s.append(Utils.generateSubmitButton());

        s.append("</form>");

        return s.toString();
    }

    public static String generateForm(Object obj, HashMap<String, ArrayList> hashMap) throws Exception {
            StringBuilder s = new StringBuilder();

            s.append(generateFormHeader(obj));

            s.append(generateFormContent(obj, hashMap));
            
            s.append(generateFormFooter());

            return s.toString();
    }

}
