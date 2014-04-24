package test6grails



import org.junit.*
import grails.test.mixin.*

@TestFor(Patient2Controller)
@Mock(Patient2)
class Patient2ControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/patient2/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.patient2InstanceList.size() == 0
        assert model.patient2InstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.patient2Instance != null
    }

    void testSave() {
        controller.save()

        assert model.patient2Instance != null
        assert view == '/patient2/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/patient2/show/1'
        assert controller.flash.message != null
        assert Patient2.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/patient2/list'

        populateValidParams(params)
        def patient2 = new Patient2(params)

        assert patient2.save() != null

        params.id = patient2.id

        def model = controller.show()

        assert model.patient2Instance == patient2
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/patient2/list'

        populateValidParams(params)
        def patient2 = new Patient2(params)

        assert patient2.save() != null

        params.id = patient2.id

        def model = controller.edit()

        assert model.patient2Instance == patient2
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/patient2/list'

        response.reset()

        populateValidParams(params)
        def patient2 = new Patient2(params)

        assert patient2.save() != null

        // test invalid parameters in update
        params.id = patient2.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/patient2/edit"
        assert model.patient2Instance != null

        patient2.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/patient2/show/$patient2.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        patient2.clearErrors()

        populateValidParams(params)
        params.id = patient2.id
        params.version = -1
        controller.update()

        assert view == "/patient2/edit"
        assert model.patient2Instance != null
        assert model.patient2Instance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/patient2/list'

        response.reset()

        populateValidParams(params)
        def patient2 = new Patient2(params)

        assert patient2.save() != null
        assert Patient2.count() == 1

        params.id = patient2.id

        controller.delete()

        assert Patient2.count() == 0
        assert Patient2.get(patient2.id) == null
        assert response.redirectedUrl == '/patient2/list'
    }
}
