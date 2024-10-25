package taskTracker.tests;

public class TestAll {
    public static void test() {
        testAdd();
        testDelete();
        testUpdate();
        testMarkDone();
        testMarkInProgress();
        testListAll();
        testListDone();
        testListToDo();
        testListInProgress();
    }

    private static void testListInProgress() {
        ListInProgressCommandTest test = new ListInProgressCommandTest();
        test.testExecute();
    }

    private static void testListToDo() {
        ListToDoCommandTest test = new ListToDoCommandTest();
        test.testExecute();
    }

    private static void testListDone() {
        ListDoneCommandTest test = new ListDoneCommandTest();
        test.testExecute();
    }

    public static void testListAll() {
        ListAllCommandTest test = new ListAllCommandTest();
        test.testExecute();
    }

    public static void testAdd() {
        AddCommandTest tAdd = new AddCommandTest();
        tAdd.testExecute();
        try {
            tAdd.testExecuteInvalidInput();
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    public static void testDelete() {
        DeleteCommandTest tDelete = new DeleteCommandTest();

        tDelete.testExecute();
        try {
            tDelete.testExecuteInvalidInput();
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    public static void testUpdate() {
        UpdateCommandTest tUpdate = new UpdateCommandTest();
        tUpdate.testExecute();
        try {
            tUpdate.testExecuteInvalidInput();
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    public static void testMarkDone() {
        MarkDoneCommandTest tMarkDone = new MarkDoneCommandTest();
        tMarkDone.testExecute();

        try {
            tMarkDone.testExecuteInvalidInput();
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    public static void testMarkInProgress() {
        MarkInProgressCommandTest tMarkInProgress = new MarkInProgressCommandTest();
        tMarkInProgress.testExecute();
        try {
            tMarkInProgress.testExecuteInvalidInput();
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
}
