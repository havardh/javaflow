package com.github.havardh.javaflow.model;

import com.github.havardh.javaflow.model.ModelWithInnerClasses.Child1.Child2;
import com.github.havardh.javaflow.model.ModelWithInnerClasses.Child1.Child2.Child3;
import com.github.havardh.javaflow.model.ModelWithInnerClasses.Child1.Child2.Child4;

public class ModelWithInnerClasses {
  class Child1 {
    class Child2 {
      class Child3 {
        private String field1;

        public String getField1() {
          return field1;
        }
      }

      class Child4 extends Child3 {
      }

      private int field1;

      public int getField1() {
        return field1;
      }
    }
  }

  private Child1 child1;
  private Child2 child2;
  private Child3 child3;
  private Child4 child4;

  public Child1 getChild1() {
    return child1;
  }

  public Child2 getChild2() {
    return child2;
  }

  public Child3 getChild3() {
    return child3;
  }

  public Child4 getChild4() {
    return child4;
  }
}
