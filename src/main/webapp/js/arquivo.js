
<script type="text/javascript">

    function handleCadastroResult(args) {
        if (!args.validationFailed) {
            // Limpe os campos
            document.getElementById('form:cargo').value = '';
            document.getElementById('form:salario').value = '';
        }
    }
</script>