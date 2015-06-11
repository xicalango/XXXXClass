package xx.xxccllaass;

import xx.xxclass.XXClassManager;
import xx.xxclass.XXInstance;

public class XXXXClassManager {

  private static interface XXXXMethod {
    XXInstance $(XXClassManager metaClassManager, XXInstance classManager, XXInstance self, XXInstance pars);
  }

  public static void main(String[] args) {

    XXClassManager cm = setup();

    XXInstance xxcm = cm.newInstance("ClassManager");

    xxcm.$("newClassDef", cm.$$("test2"))

    .$("defFn", cm.$$("setName"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      self.$("put", mcm.$$("name"), pars.$("get", mcm.$$(0)));
      return self;
    }))

    .$("defFn", cm.$$("hellas"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      return mgr.$("wrap", mcm.$$("Hallo ").$("..", self.$("get", mcm.$$("name"))));
    }));

    xxcm.$("newClassDef", cm.$$("test"))

    .$("defFn", cm.$$("halloWeld"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      return mgr.$("wrap", mcm.$$("Hallo Welt"));
    }))

    .$("defFn", cm.$$("halloWeld2"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      return mgr.$("wrap", mcm.$$("Hallo ").$("..", pars.$("get", mcm.$$(0))));
    }))

    .$("defFn", cm.$$("lustig"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      XXInstance inst = mgr.$("newInstance", cm.$$("test2"));
      inst.$("call", mcm.$$("setName"), pars);
      return inst;
    }));

    cm.getXXClass("Instance").redefFn("call", old -> (mgr, self, pars) -> {
      System.out.println("call " + pars[0]);
      return old.$(mgr, self, pars);
    });

    XXInstance testClassInstance = xxcm.$("newInstance", cm.$$("test"));

    XXInstance hallo = testClassInstance.$("call", cm.$$("halloWeld"));

    XXInstance hallo2 = testClassInstance.$("call", cm.$$("halloWeld2"), cm.newArrayOf(cm.$$("Alex")));

    System.out.println(xxcm);
    System.out.println(testClassInstance);
    System.out.println(testClassInstance.get("class"));
    System.out.println(hallo);
    System.out.println(hallo.get("class"));
    System.out.println(hallo.get("class").get("name"));
    System.out.println(hallo.getXXClass().getName());
    System.out.println(hallo.$("unwrap"));
    System.out.println(hallo.$("unwrap").getXXClass().getName());
    System.out.println(hallo2.$("unwrap"));

    final XXInstance test2Instance = testClassInstance.$("call", cm.$$("lustig"), cm.newArrayOf(cm.$$("AlexX")));

    System.out.println(test2Instance);
    System.out.println(test2Instance.$("call", cm.$$("hellas")).$("unwrap"));

    xxcm.$("newClassDef", cm.$$("Modifier"))

    .$("defFn", cm.$$("changeImplOfClassManager"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {

      mgr.getXXClass().redefFn("newClassDef", old -> (mgr2, self2, pars2) -> {
        XXInstance newClassDef = old.$(mgr2, self2, pars2);
        newClassDef.$("defFn", mgr2.$$("evil"), mgr2.$$((XXXXMethod) (mcm2, mgr3, self3, pars3) -> {
          return pars.$("get", mcm2.$$(0));
        }));

        newClassDef.$("defFn", mgr2.$$("evil2"), mgr2.$$((XXXXMethod) (mcm2, mgr3, self3, pars3) -> {
          return self2;
        }));

        newClassDef.$("defFn", mgr2.$$("evil3"), mgr2.$$((XXXXMethod) (mcm2, mgr3, self3, pars3) -> {
          return mgr3.$("wrap", mcm2.$$(mgr3.$("wrap", mcm2.$$(mcm2.$$("A")))));
        }));

        return newClassDef;
      });

      return self;
    }))

    .$("defFn", cm.$$("addRedef"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      mcm.getXXClass("Class").defFn("redef", (mgr2, self2, pars2) -> {
        self2.get("functionMap").put(pars2[0], pars2[1]);
        return self2;
      });

      return self;
    }));

    XXInstance evilClass = xxcm.$("newInstance", cm.$$("Modifier"));

    evilClass.$("call", cm.$$("changeImplOfClassManager"), cm.newArrayOf(cm.$$("Alex")));

    xxcm.$("newClassDef", cm.$$("ModTest"));

    XXInstance modTest = xxcm.$("newInstance", cm.$$("ModTest"));

    XXInstance sth = modTest.$("call", cm.$$("evil"));

    System.out.println(sth);

    XXInstance sth2 = modTest.$("call", cm.$$("evil2"));

    System.out.println(sth2);

    XXInstance sth3 = modTest.$("call", cm.$$("evil3"));

    System.out.println(sth3);
    System.out.println(sth3.$("unwrap").getXXClass().getName());

    modTest.$("call", cm.$$("evil3"));

    evilClass.$("call", cm.$$("addRedef"));

    evilClass.get("class").$("redef", cm.$$("addRedef"), cm.$$((XXXXMethod) (mcm, mgr, self, pars) -> {
      System.out.println(self);
      return self;
    }));

    evilClass.$("call", cm.$$("addRedef"));

  }

  private static XXClassManager setup() {
    XXClassManager cm = new XXClassManager();

    cm.newClassDef("Map")

    .defFn("put", (mgr, self, pars) -> {
      self.put(pars[0], pars[1]);
      return self;
    })

    .defFn("get", (mgr, self, pars) -> {
      return self.get(pars[0]);
    });

    cm.newClassDef("ClassManager")

    .redefFn("__init__", old -> (mgr, self, pars) -> {
      self.put("classCache", mgr.newInstance("Map"));

      self.put("wrapperClass", mgr.newInstance("WrapperClass", self, mgr.$$("Wrapper")));

      return self;
    })

    .defFn("newClassDef", (mgr, self, pars) -> {
      if (!self.get("classCache").get(pars[0]).isNull()) {
        throw new IllegalArgumentException("Class already exists: " + pars[0].toString());
      }

      XXInstance newClass = mgr.newInstance("Class", self, pars[0]);

      self.get("classCache").$("put", pars[0], newClass);

      return newClass;
    })

    .defFn("newInstance", (mgr, self, pars) -> {
      if (self.get("classCache").get(pars[0]).isNull()) {
        throw new IllegalArgumentException("No such class: " + pars[0].toString());
      }

      return self.get("classCache").get(pars[0]).$("newInstance");
    })

    .defFn("wrap", (mgr, self, pars) -> {
      return self.get("wrapperClass").$("newInstance", pars[0]);
    });

    cm.newClassDef("Class")

    .redefFn("__init__", old -> (mgr, self, pars) -> {
      self.put("cm", pars[0]);
      self.put("name", pars[1]);
      self.put("functionMap", mgr.newInstance("Map"));
      self.$("defFn", mgr.$$("__str__"), mgr.$$((XXXXMethod) (mcm, mgr2, self2, pars2) -> {
        return mgr2.$("wrap", mcm.$$("Instance of ").$("..", self.get("name")));
      }));
      return self;
    })

    .defFn("newInstance", (mgr, self, pars) -> {
      return mgr.newInstance("Instance", self);
    })

    .defFn("defFn", (mgr, self, pars) -> {
      if (!self.get("functionMap").get(pars[0]).isNull()) {
        throw new IllegalArgumentException("Method already defined: " + pars[0].toString());
      }

      self.get("functionMap").put(pars[0], pars[1]);

      return self;
    });

    cm.newClassDef("WrapperClass", "Class")

    .redefFn("__init__", old -> (mgr, self, pars) -> {
      mgr.getXXClass("Class").call("__init__", self, pars);
      return self;
    })

    .defFn("newInstance", (mgr, self, pars) -> {
      return mgr.newInstance("WrapperInstance", self, pars[0]);
    });

    cm.newClassDef("Instance")

    .redefFn("__init__", old -> (mgr, self, pars) -> {
      self.put("class", pars[0]);
      self.put("fields", mgr.newInstance("Map"));
      return self;
    })

    .redefFn("__str__", old -> (mgr, self, pars) -> {
      return self.$("call", mgr.$$("__str__"), mgr.newArrayOf()).$("unwrap");
    })

    .defFn("call", (mgr, self, pars) -> {
      XXInstance method = self.get("class").get("functionMap").get(pars[0]);
      XXXXMethod realMethod = method.asJavaObject(XXXXMethod.class);

      XXInstance parArray;

      if (pars.length == 2) {
        parArray = pars[1];
      } else {
        parArray = mgr.newArrayOf();
      }
      return realMethod.$(self.getXXClass().getClassManager(), self.get("class").get("cm"), self, parArray);
    })

    .defFn("put", (mgr, self, pars) -> {
      self.get("fields").put(pars[0], pars[1]);
      return pars[1];
    })

    .defFn("get", (mgr, self, pars) -> {
      return self.get("fields").get(pars[0]);
    });

    cm.newClassDef("WrapperInstance", "Instance")

    .redefFn("__init__", old -> (mgr, self, pars) -> {
      mgr.getXXClass("Instance").call("__init__", self, pars);
      self.put("wrapped", pars[1]);
      return self;
    })

    .defFn("unwrap", (mgr, self, pars) -> {
      return self.get("wrapped");
    });

    return cm;
  }
}
