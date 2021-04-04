resource "google_service_account" "this" {
  account_id   = "spinnaker"
  display_name = "Spinnaker"
}

resource "google_project_iam_binding" "this" {
  role = "roles/storage.admin"
  members = [
    "serviceAccount:${google_service_account.this.email}"
  ]
}
