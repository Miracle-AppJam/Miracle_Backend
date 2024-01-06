resource "aws_vpc" "miracle_vpc" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "miracle-vpc"
  }
}