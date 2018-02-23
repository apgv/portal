<template>
    <div v-if="showConfirmDeleteModal"
         class="modal"
         :class="{'is-active': showConfirmDeleteModal}">
        <div @click="$emit('close')"
             class="modal-background"></div>
        <div class="modal-content">
            <div class="box">
                <article class="media">
                    <div class="media-content">
                        <div class="content">
                            <p>
                                Er du sikker på at du vil slette {{toBeDeletedDisplayName}}?
                            </p>
                        </div>
                        <div>
                            <a @click="deleteFunction(toBeDeleted)"
                               class="button is-danger">Slett</a>
                            <a @click="$emit('close')"
                               class="button is-text">Avbryt</a>
                        </div>
                    </div>
                </article>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'confirm-delete-modal',
    props: ['showConfirmDeleteModal', 'deleteFunction', 'toBeDeleted', 'toBeDeletedDisplayName']
}
</script>
