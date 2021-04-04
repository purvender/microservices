resource "google_service_account" "this" {
  account_id   = "halyard"
  display_name = "Halyard"
}

resource "google_project_iam_binding" "logging_log_writer" {
  role = "roles/logging.logWriter"
  members = [
    "serviceAccount:${google_service_account.this.email}"
  ]
}

resource "google_project_iam_binding" "monitoring_metric_writer" {
  role = "roles/monitoring.metricWriter"
  members = [
    "serviceAccount:${google_service_account.this.email}"
  ]
}

resource "google_compute_instance" "this" {
  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }
  machine_type = "e2-highmem-2"
  metadata_startup_script = <<EOF
#!/bin/bash
# COMMON
apt-get update; apt-get install -y software-properties-common apt-transport-https ca-certificates gnupg
# GGLOUD
curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key --keyring /usr/share/keyrings/cloud.google.gpg add -
apt-get update && apt-get install google-cloud-sdk
# KUBECTL
curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.17.0/bin/linux/amd64/kubectl
chmod +x ./kubectl
mv ./kubectl /usr/local/bin/kubectl
# SPINNAKER
wget -O- https://apt.corretto.aws/corretto.key | apt-key add -
add-apt-repository 'deb https://apt.corretto.aws stable main'
apt-get update; apt-get install -y java-11-amazon-corretto-jdk
curl -O https://raw.githubusercontent.com/spinnaker/halyard/master/install/debian/InstallHalyard.sh
EOF
  name         = "halyard"
  network_interface {
    access_config {
    }
    network = "default"
  }
  service_account {
    email = google_service_account.this.email
    scopes = [
      "cloud-platform"
    ]
  }
  zone         = var.zone
}
