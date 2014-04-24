package test6grails

import org.springframework.dao.DataIntegrityViolationException

class Patient2Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [patient2InstanceList: Patient2.list(params), patient2InstanceTotal: Patient2.count()]
    }

    def create() {
        [patient2Instance: new Patient2(params)]
    }

    def save() {
        def patient2Instance = new Patient2(params)
        if (!patient2Instance.save(flush: true)) {
            render(view: "create", model: [patient2Instance: patient2Instance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'patient2.label', default: 'Patient2'), patient2Instance.id])
        redirect(action: "show", id: patient2Instance.id)
    }

    def show(Long id) {
        def patient2Instance = Patient2.get(id)
        if (!patient2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient2.label', default: 'Patient2'), id])
            redirect(action: "list")
            return
        }

        [patient2Instance: patient2Instance]
    }

    def edit(Long id) {
        def patient2Instance = Patient2.get(id)
        if (!patient2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient2.label', default: 'Patient2'), id])
            redirect(action: "list")
            return
        }

        [patient2Instance: patient2Instance]
    }

    def update(Long id, Long version) {
        def patient2Instance = Patient2.get(id)
        if (!patient2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient2.label', default: 'Patient2'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (patient2Instance.version > version) {
                patient2Instance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'patient2.label', default: 'Patient2')] as Object[],
                          "Another user has updated this Patient2 while you were editing")
                render(view: "edit", model: [patient2Instance: patient2Instance])
                return
            }
        }

        patient2Instance.properties = params

        if (!patient2Instance.save(flush: true)) {
            render(view: "edit", model: [patient2Instance: patient2Instance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'patient2.label', default: 'Patient2'), patient2Instance.id])
        redirect(action: "show", id: patient2Instance.id)
    }

    def delete(Long id) {
        def patient2Instance = Patient2.get(id)
        if (!patient2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient2.label', default: 'Patient2'), id])
            redirect(action: "list")
            return
        }

        try {
            patient2Instance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'patient2.label', default: 'Patient2'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'patient2.label', default: 'Patient2'), id])
            redirect(action: "show", id: id)
        }
    }
}
