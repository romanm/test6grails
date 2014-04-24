package test6grails



import org.junit.*
import grails.test.mixin.*

@TestFor(Patient1Controller)
@Mock(Patient1)
class Patient1ControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/patient1/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.patient1InstanceList.size() == 0
        assert model.patient1InstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.patient1Instance != null
    }

    void testSave() {
        controller.save()

        assert model.patient1Instance != null
        assert view == '/patient1/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/patient1/show/1'
        assert controller.flash.message != null
        assert Patient1.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/patient1/list'

        populateValidParams(params)
        def patient1 = new Patient1(params)

        assert patient1.save() != null

        params.id = patient1.id

        def model = controller.show()

        assert model.patient1Instance == patient1
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/patient1/list'

        populateValidParams(params)
        def patient1 = new Patient1(params)

        assert patient1.save() != null

        params.id = patient1.id

        def model = controller.edit()

        assert model.patient1Instance == patient1
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/patient1/list'

        response.reset()

        populateValidParams(params)
        def patient1 = new Patient1(params)

        assert patient1.save() != null

        // test invalid parameters in update
        params.id = patient1.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/patient1/edit"
        assert model.patient1Instance != null

        patient1.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/patient1/show/$patient1.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        patient1.clearErrors()

        populateValidParams(params)
        params.id = patient1.id
        params.version = -1
        controller.update()

        assert view == "/patient1/edit"
        assert model.patient1Instance != null
        assert model.patient1Instance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/patient1/list'

        response.reset()

        populateValidParams(params)
        def patient1 = new Patient1(params)

        assert patient1.save() != null
        assert Patient1.count() == 1

        params.id = patient1.id

        controller.delete()

        assert Patient1.count() == 0
        assert Patient1.get(patient1.id) == null
        assert response.redirectedUrl == '/patient1/list'
    }
}
